package com.crisptendies.redisdemo11.url;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final UrlShorteningService urlShorteningService;

    @PostMapping("/url")
    Mono<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest shortenUrlRequest) {
        String originalUrl = shortenUrlRequest.getOriginalUrl();
        return urlShorteningService
                .shortenUrl(originalUrl)
                .map(shortenedUrl -> new ShortenUrlResponse(shortenedUrl, originalUrl));
    }

    @GetMapping("/{shortenedUrl}")
    Mono<ResponseEntity<Object>> getUrl(@PathVariable String shortenedUrl) {
        return urlShorteningService
                .getUrl(shortenedUrl)
                .map(url -> ResponseEntity
                        .status(HttpStatus.PERMANENT_REDIRECT)
                        .header("Location", url.getOriginalUrl())
                        .build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{shortenedUrl}")
    Mono<Boolean> deleteUrl(@PathVariable String shortenedUrl) {
        return urlShorteningService
                .deleteUrl(shortenedUrl);
    }
}
