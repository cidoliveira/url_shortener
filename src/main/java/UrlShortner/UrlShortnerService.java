package UrlShortner;

import lombok.AllArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UrlShortnerService {

    private final UrlShortnerRepository urlShortnerRepository;

    public String randomString() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .get();

        return generator.generate(4);
    }

    public String putLink(String initialUrl) {
        String randomUrlKey = randomString();

        UrlShortnerModel model = new UrlShortnerModel();
        model.setReceivedUrl(initialUrl);
        model.setShortnedUrl(randomUrlKey);

        urlShortnerRepository.save(model);

        return randomUrlKey;
    }

    public String retrieveLink(String urlKey) {
        return urlShortnerRepository.findByShortnedUrl(urlKey)
                .map(UrlShortnerModel::getReceivedUrl)
                .orElse(null);
    }
}