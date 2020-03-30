package com.crisptendies.redisdemo11.url;

import lombok.Value;

@Value
public class ShortenUrlResponse {
    private String shortenedUrl;
    private String originalUrl;
}
