package com.crisptendies.redisdemo11.url;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlRequest {
    private String originalUrl;
}
