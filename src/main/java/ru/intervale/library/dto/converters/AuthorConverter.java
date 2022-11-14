package ru.intervale.library.dto.converters;

import org.springframework.stereotype.Component;
import ru.intervale.library.dto.AuthorDto;
import ru.intervale.library.entities.Author;

@Component
public class AuthorConverter {

    public AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public Author fromDto(AuthorDto authorDto) {
        return Author.builder()
                .id(authorDto.getId())
                .firstName(authorDto.getFirstName())
                .lastName(authorDto.getLastName())
                .build();
    }
}
