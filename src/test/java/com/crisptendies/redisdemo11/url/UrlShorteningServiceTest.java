package com.crisptendies.redisdemo11.url;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

class UrlShorteningServiceTest {
    private static String domain = "http://www.yeet.com/";
    private String shortenedUrl = "a1b2c3d";
    private String originalUrl = "https://www.farewell.com";
    private static UrlRepository urlRepository;
    private static UrlShorteningService urlShorteningService;

    @BeforeAll
    private static void setup() {
        urlRepository = Mockito.mock(UrlRepository.class);
        urlShorteningService = new UrlShorteningService(domain, urlRepository);
    }

    @Test
    public void shortenUrl_shouldReturnShortenedUrlWithDomain() {
        Mockito.when(urlRepository.save(any(Url.class)))
                .thenReturn(Mono.just(new Url(shortenedUrl, originalUrl)));

        StepVerifier
                .create(urlShorteningService.shortenUrl(originalUrl))
                .expectNext(domain + shortenedUrl)
                .verifyComplete();
    }

    @Test
    public void getUrl_shouldReturnUrlObject() {
        Mockito.when(urlRepository.findByShortenedUrl(shortenedUrl))
                .thenReturn(Mono.just(new Url(shortenedUrl, originalUrl)));

        StepVerifier
                .create(urlShorteningService.getUrl(shortenedUrl))
                .expectNext(new Url(shortenedUrl, originalUrl))
                .verifyComplete();
    }

    @Test
    public void deleteUrl_shouldReturnTrue() {
        Mockito.when(urlRepository.deleteByShortenedUrl(any(String.class)))
                .thenReturn(Mono.just(true));

        StepVerifier
                .create(urlRepository.deleteByShortenedUrl("AABBCC1"))
                .expectNext(true)
                .verifyComplete();
    }
}