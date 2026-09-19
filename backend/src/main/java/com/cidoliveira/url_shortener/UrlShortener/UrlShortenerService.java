package com.cidoliveira.url_shortener.UrlShortener;

import lombok.AllArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UrlShortenerService {

    private final UrlShortenerRepository urlShortenerRepository;
    private final UrlShortenerMapper urlShortenerMapper;

    public String randomString() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .get();

        return generator.generate(4);
    }

    public UrlShortenerDTO putLink(UrlShortenerDTO dto) {

        UrlShortenerModel model = urlShortenerMapper.toModel(dto);

        String url = model.getReceivedUrl();

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        model.setReceivedUrl(url);
        model.setShortenedUrl(randomString());

        UrlShortenerModel savedModel = urlShortenerRepository.save(model);

        return urlShortenerMapper.toDTO(savedModel);
    }

    public String retrieveLink(String urlKey) {
        return urlShortenerRepository.findByShortenedUrl(urlKey)
                .map(UrlShortenerModel::getReceivedUrl)
                .orElse(null);
    }
}