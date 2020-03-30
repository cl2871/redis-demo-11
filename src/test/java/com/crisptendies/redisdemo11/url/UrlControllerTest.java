package com.crisptendies.redisdemo11.url;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@WebFluxTest(controllers = UrlController.class)
class UrlControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UrlShorteningService urlShorteningService;

    private String domain = "http://localhost:8080/";
    private String shortenedUrl = "12cdEF7";
    private String originalUrl = "https://www.hello.com";

    @Test
    void shortenUrl() {
        Mockito.when(urlShorteningService.shortenUrl(originalUrl))
                .thenReturn(Mono.just(domain + shortenedUrl));

        ShortenUrlRequest shortenUrlRequest = new ShortenUrlRequest(originalUrl);

        webTestClient
                .post()
                .uri("/url")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(shortenUrlRequest), ShortenUrlRequest.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.shortenedUrl")
                .value(result -> assertThat(result).isEqualTo(domain + shortenedUrl))
                .jsonPath("$.originalUrl")
                .value(result -> assertThat(result).isEqualTo(originalUrl));
    }

    @Test
    void getUrl() {
        Mockito.when(urlShorteningService.getUrl(shortenedUrl))
                .thenReturn(Mono.just(new Url(shortenedUrl, originalUrl)));

        webTestClient
                .get()
                .uri("/" + shortenedUrl)
                .exchange()
                .expectStatus()
                .isPermanentRedirect()
                .expectHeader()
                .value("Location", location -> assertThat(location).isEqualTo(originalUrl));
    }

    @Test
    void deleteUrl() {
        Mockito.when(urlShorteningService.deleteUrl(shortenedUrl))
                .thenReturn(Mono.just(true));

        webTestClient
                .delete()
                .uri("/" + shortenedUrl)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
}