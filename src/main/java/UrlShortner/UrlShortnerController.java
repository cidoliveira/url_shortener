package UrlShortner;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/urlshortner")
public class UrlShortnerController {
    private UrlShortnerService urlShortnerService;

    @GetMapping("/{url}")
    public ResponseEntity<?> accessUrl(@PathVariable String url){
        String foundUrl = urlShortnerService.retrieveLink(url);

        if (foundUrl != null) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(foundUrl))
                .build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/post")
    public ResponseEntity<?> postUrl(@RequestBody String url) {

        String shortKey = urlShortnerService.putLink(url);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Your short link is: http://localhost:8080/urlshortner/" + shortKey);
    }
}
