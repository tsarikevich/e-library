package ru.intervale.library.services;

import ru.intervale.library.dto.AuthorDto;
import ru.intervale.library.dto.PrintedProductDto;

import java.util.List;
import java.util.Map;

public interface AuthorService extends BaseService<AuthorDto> {
    Map<AuthorDto, List<PrintedProductDto>> getAuthorData(long id);

    Map<AuthorDto, List<PrintedProductDto>> getAllAuthorsData();

    Map<AuthorDto, List<PrintedProductDto>> findAuthorsByLastName(String lastName);
}
