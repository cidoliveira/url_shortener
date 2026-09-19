package com.cidoliveira.url_shortener.UrlShortener;

public record UrlShortenerDTO (   Long id,
                                  String receivedUrl,
                                  String shortenedUrl){
}
