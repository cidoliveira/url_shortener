package com.cidoliveira.url_shortener.UrlShortener;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/urlshortener")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    @GetMapping("/{url}")
    public ResponseEntity<?> accessUrl(@PathVariable String url){
        String foundUrl = urlShortenerService.retrieveLink(url);

        if (foundUrl != null) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(foundUrl))
                .build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/post")
    public ResponseEntity<UrlShortenerDTO> postUrl(@RequestBody UrlShortenerDTO dto) {

        UrlShortenerDTO createdUrl = urlShortenerService.putLink(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUrl);
    }
}
