package ru.intervale.library.dto.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.intervale.library.dto.BookDto;
import ru.intervale.library.entities.Book;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookConverter {
    private final AuthorConverter authorConverter;
    private final PublisherConverter publisherConverter;

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .edition(book.getEdition())
                .date(book.getDate())
                .publisher(publisherConverter.toDto(book.getPublisher()))
                .authors(book.getAuthors().stream().map(authorConverter::toDto).collect(Collectors.toSet()))
                .build();
    }

    public Book fromDto(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .edition(bookDto.getEdition())
                .date(bookDto.getDate())
                .publisher(publisherConverter.fromDto(bookDto.getPublisher()))
                .authors(bookDto.getAuthors().stream().map(authorConverter::fromDto).collect(Collectors.toSet()))
                .build();
    }
}
