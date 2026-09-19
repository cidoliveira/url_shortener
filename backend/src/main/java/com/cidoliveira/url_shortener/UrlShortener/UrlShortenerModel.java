package com.cidoliveira.url_shortener.UrlShortener;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_links")
public class UrlShortenerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String receivedUrl;

    @Column(name = "shortned_url", unique = true)
    private String shortenedUrl;

}
