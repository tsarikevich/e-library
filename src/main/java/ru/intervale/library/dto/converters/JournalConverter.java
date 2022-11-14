package ru.intervale.library.dto.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.intervale.library.dto.JournalDto;
import ru.intervale.library.entities.Journal;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JournalConverter {
    private final AuthorConverter authorConverter;
    private final PublisherConverter publisherConverter;

    public JournalDto toDto(Journal journal) {
        return JournalDto.builder()
                .id(journal.getId())
                .title(journal.getTitle())
                .number(journal.getNumber())
                .date(journal.getDate())
                .publisher(publisherConverter.toDto(journal.getPublisher()))
                .authors(journal.getAuthors().stream().map(authorConverter::toDto).collect(Collectors.toSet()))
                .build();
    }

    public Journal fromDto(JournalDto journalDto) {
        return Journal.builder()
                .id(journalDto.getId())
                .title(journalDto.getTitle())
                .number(journalDto.getNumber())
                .date(journalDto.getDate())
                .publisher(publisherConverter.fromDto(journalDto.getPublisher()))
                .authors(journalDto.getAuthors().stream().map(authorConverter::fromDto).collect(Collectors.toSet()))
                .build();
    }
}
