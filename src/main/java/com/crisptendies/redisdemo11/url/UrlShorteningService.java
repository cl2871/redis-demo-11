package com.crisptendies.redisdemo11.url;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import reactor.core.publisher.Mono;

@Service
public class UrlShorteningService {
    private final String domain;
    private final UrlRepository urlRepository;

    public UrlShorteningService(@Value("${app.domain}") String domain, UrlRepository urlRepository) {
        this.domain = domain;
        this.urlRepository = urlRepository;
    }

    // Save a shortened url that maps to the original url.
    Mono<String> shortenUrl(String originalUrl) {
        // Use md5 to hash the original url and then take the first 7 characters.
        String shortenedUrl = DigestUtils
                .md5DigestAsHex(originalUrl.getBytes())
                .substring(0, 7);

        // Return the shortened url with app domain prepended.
        return urlRepository
                .save(new Url(shortenedUrl, originalUrl))
                .map(result -> domain + result.getShortenedUrl());
    }

    Mono<Url> getUrl(String shortenedUrl) {
        return urlRepository.findByShortenedUrl(shortenedUrl);
    }

    Mono<Boolean> deleteUrl(String shortenedUrl) {
        return urlRepository.deleteByShortenedUrl(shortenedUrl);
    }
}
