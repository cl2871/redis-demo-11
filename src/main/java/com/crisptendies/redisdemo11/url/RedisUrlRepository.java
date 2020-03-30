package com.crisptendies.redisdemo11.url;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RedisUrlRepository implements UrlRepository {
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Override
    public Mono<Url> save(Url url) {
        return reactiveRedisTemplate.opsForValue()
                .set(url.getShortenedUrl(), url.getOriginalUrl())
                .map(ignored -> url);
    }

    @Override
    public Mono<Url> findByShortenedUrl(String shortenedUrl) {
        return reactiveRedisTemplate.opsForValue()
                .get(shortenedUrl)
                .map(originalUrl -> new Url(shortenedUrl, originalUrl));
    }

    @Override
    public Mono<Boolean> deleteByShortenedUrl(String shortenedUrl) {
        return reactiveRedisTemplate.opsForValue().delete(shortenedUrl);
    }
}
