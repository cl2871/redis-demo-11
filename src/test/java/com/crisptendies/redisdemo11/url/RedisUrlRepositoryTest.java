package com.crisptendies.redisdemo11.url;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class RedisUrlRepositoryTest {
    @Autowired
    private RedisUrlRepository redisUrlRepository;
    private Url url = new Url("a1B2c3D", "https://www.goodbye.com");

    @Test
    public void save_shouldReturnSameUrl_asProvidedUrlObject() {
        StepVerifier
                .create(redisUrlRepository.save(url))
                .expectNext(url)
                .verifyComplete();
    }

    @Test
    public void findByShortenedUrl_shouldReturnSameUrl_asSavedUrl() {
        StepVerifier
                .create(redisUrlRepository
                        .save(url)
                        .flatMap(ignored -> redisUrlRepository.findByShortenedUrl(url.getShortenedUrl())))
                .expectNext(url)
                .verifyComplete();
    }

    @Test
    public void deleteByShortenedUrl_shouldReturnTrue() {
        StepVerifier
                .create(redisUrlRepository
                        .save(url)
                        .flatMap(ignored -> redisUrlRepository.deleteByShortenedUrl("a1B2c3D")))
                .expectNext(true)
                .verifyComplete();
    }
}