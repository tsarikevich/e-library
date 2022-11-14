package ru.intervale.library.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"publisher", "authors"})
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PrintedProductDto {
    @Positive
    private long id;
    private String title;
    private LocalDate date;
    private PublisherDto publisher;
    private Set<AuthorDto> authors;
}
