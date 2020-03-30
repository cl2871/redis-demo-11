package com.crisptendies.redisdemo11.url;

import lombok.Value;

@Value
public class Url {
    private String shortenedUrl;
    private String originalUrl;
}
