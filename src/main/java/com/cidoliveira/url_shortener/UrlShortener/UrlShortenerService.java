package com.cidoliveira.url_shortener.UrlShortener;

import lombok.AllArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UrlShortenerService {

    private final UrlShortenerRepository urlShortenerRepository;

    public String randomString() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .get();

        return generator.generate(4);
    }

    public String putLink(String initialUrl) {
        String randomUrlKey = randomString();

        UrlShortenerModel model = new UrlShortenerModel();
        model.setReceivedUrl(initialUrl);
        model.setShortenedUrl(randomUrlKey);

        urlShortenerRepository.save(model);

        return randomUrlKey;
    }

    public String retrieveLink(String urlKey) {
        return urlShortenerRepository.findByShortenedUrl(urlKey)
                .map(UrlShortenerModel::getReceivedUrl)
                .orElse(null);
    }
}