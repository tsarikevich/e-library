package ru.intervale.library.services.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.intervale.library.dto.BookDto;
import ru.intervale.library.dto.converters.BookConverter;
import ru.intervale.library.entities.Book;
import ru.intervale.library.repositiries.AuthorRepository;
import ru.intervale.library.repositiries.BookRepository;
import ru.intervale.library.repositiries.PublisherRepository;
import ru.intervale.library.services.BookService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@Log4j
public class BookServiceImpl extends PrintedProductBaseService implements BookService {
    private final BookRepository bookRepository;
    private final BookConverter bookConverter;

    public BookServiceImpl(AuthorRepository authorRepository, PublisherRepository publisherRepository, BookRepository bookRepository, BookConverter bookConverter) {
        super(authorRepository, publisherRepository);
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
    }

    @Override
    public BookDto getBookData(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return bookConverter.toDto(book.get());
        } else {
            log.warn("The book wasn't found with id = " + id);
            return null;
        }
    }

    @Override
    public List<BookDto> getAllBooksData() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookConverter::toDto).toList();
    }

    @Override
    public BookDto create(BookDto bookDto) {
        try {
            Book book = bookConverter.fromDto(bookDto);
            if (Optional.ofNullable(book.getTitle()).isEmpty()) {
                log.warn("The book can't be created because title is null");
            } else {
                Book createdBook = bookRepository.save(book);
                updatePrintedProduct(createdBook);
                return bookConverter.toDto(createdBook);
            }
        } catch (Exception e) {
            log.error("The book wasn't created: ", e);
        }
        return null;
    }

    @Override
    public BookDto update(BookDto bookDto) {
        try {
            Book book = bookConverter.fromDto(bookDto);
            if (bookRepository.existsById(bookDto.getId())) {
                Book updatedBook = bookRepository.save(book);
                return bookConverter.toDto(updatedBook);
            } else {
                log.warn("The book can't be updated because it doesn't exist in the DB");
            }
        } catch (Exception e) {
            log.error("The book wasn't updated: ", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            log.error("The book wasn't deleted: ", e);
            return false;
        }
        return true;
    }

    @Override
    public BookDto findBookByTitle(String title) {
        Optional<Book> book = bookRepository.findBookByTitle(title);
        return book.map(bookConverter::toDto).orElse(null);
    }

    @Override
    public List<BookDto> findBooksByDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Book> books = bookRepository.findAllByDate(localDate);
            return books.stream().map(bookConverter::toDto).toList();
        } catch (Exception e) {
            log.error("The book wasn't got by date: ", e);
        }
        return emptyList();
    }
}
