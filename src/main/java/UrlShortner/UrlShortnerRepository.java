package UrlShortner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlShortnerRepository extends JpaRepository<UrlShortnerModel, Long> {
    Optional<UrlShortnerModel> findByShortnedUrl(String shortnedUrl);
}
