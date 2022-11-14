package ru.intervale.library.dto.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.intervale.library.dto.NewspaperDto;
import ru.intervale.library.entities.Newspaper;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewspaperConverter {
    private final AuthorConverter authorConverter;
    private final PublisherConverter publisherConverter;

    public NewspaperDto toDto(Newspaper newspaper) {
        return NewspaperDto.builder()
                .id(newspaper.getId())
                .title(newspaper.getTitle())
                .number(newspaper.getNumber())
                .date(newspaper.getDate())
                .publisher(publisherConverter.toDto(newspaper.getPublisher()))
                .authors(newspaper.getAuthors().stream().map(authorConverter::toDto).collect(Collectors.toSet()))
                .build();
    }

    public Newspaper fromDto(NewspaperDto newspaperDto) {
        return Newspaper.builder()
                .id(newspaperDto.getId())
                .title(newspaperDto.getTitle())
                .number(newspaperDto.getNumber())
                .date(newspaperDto.getDate())
                .publisher(publisherConverter.fromDto(newspaperDto.getPublisher()))
                .authors(newspaperDto.getAuthors().stream().map(authorConverter::fromDto).collect(Collectors.toSet()))
                .build();
    }
}
