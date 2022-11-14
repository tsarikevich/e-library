package ru.intervale.library.dto.converters;

import org.springframework.stereotype.Component;
import ru.intervale.library.dto.PublisherDto;
import ru.intervale.library.entities.Publisher;

@Component
public class PublisherConverter {

    public PublisherDto toDto(Publisher publisher) {
        return PublisherDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }

    public Publisher fromDto(PublisherDto publisherDto) {
        return Publisher.builder()
                .id(publisherDto.getId())
                .name(publisherDto.getName())
                .build();
    }
}
