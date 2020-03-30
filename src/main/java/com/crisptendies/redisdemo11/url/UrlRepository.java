package com.crisptendies.redisdemo11.url;

import reactor.core.publisher.Mono;

public interface UrlRepository {
    Mono<Url> save(Url url);
    Mono<Url> findByShortenedUrl(String shortenedUrl);
    Mono<Boolean> deleteByShortenedUrl(String shortenedUrl);
}
