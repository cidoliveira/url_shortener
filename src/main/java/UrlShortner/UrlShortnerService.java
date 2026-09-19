package UrlShortner;

import org.apache.commons.text.RandomStringGenerator;import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class UrlShortnerService {
    private UrlShortnerRepository urlShortnerRepository;
    private Map<String, String> keyValueUrlHashmap;
    private Map<String, String> valueKeyUrlHashmap;

    public String randomString() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z').get();
        return generator.generate(10);
    }

    public void putLink(String initialUrl) {
        while(true) {
            String randomUrlKey = randomString();
            if (!(keyValueUrlHashmap.containsKey(randomUrlKey))){
                keyValueUrlHashmap.put(initialUrl, randomUrlKey);
                valueKeyUrlHashmap.put(randomUrlKey, initialUrl);
                break;
            }
        }
    }

    public String retrieveLink(String urlKey) {
        if (keyValueUrlHashmap.containsKey(urlKey)) {
            return keyValueUrlHashmap.get(urlKey);
        }
        return null;
    }

    public String retrieveKey(String urlValue) {
        if (keyValueUrlHashmap.containsValue(urlValue)) {
            return valueKeyUrlHashmap.get(urlValue);
        }
        return null;
    }


}
