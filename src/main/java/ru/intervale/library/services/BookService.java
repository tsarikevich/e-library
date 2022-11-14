package ru.intervale.library.services;

import ru.intervale.library.dto.BookDto;

import java.util.List;

public interface BookService extends BaseService<BookDto> {
    BookDto getBookData(long id);

    List<BookDto> getAllBooksData();

    BookDto findBookByTitle(String title);

    List<BookDto> findBooksByDate(String date);
}
