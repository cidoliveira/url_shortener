package com.cidoliveira.url_shortener.UrlShortener;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlShortenerRepository extends JpaRepository<UrlShortenerModel, Long> {
    Optional<UrlShortenerModel> findByShortenedUrl(String shortenedUrl);
}
