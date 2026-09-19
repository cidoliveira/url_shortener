package com.cidoliveira.url_shortener.UrlShortener;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlShortenerMapper {
    UrlShortenerDTO toDTO(UrlShortenerModel model);

    UrlShortenerModel toModel(UrlShortenerDTO dto);
}
