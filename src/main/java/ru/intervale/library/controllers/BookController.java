package ru.intervale.library.controllers;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.library.dto.BookDto;
import ru.intervale.library.services.BookService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Validated
@RestController
@RequestMapping("/books")
@Log4j
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookData(@Valid @Positive @PathVariable Long id) {
        BookDto bookDTO = bookService.getBookData(id);
        if (Optional.ofNullable(bookDTO).isPresent()) {
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooksData() {
        List<BookDto> booksDTO = bookService.getAllBooksData();
        if (isNotEmpty(booksDTO)) {
            return new ResponseEntity<>(booksDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto createdBook = bookService.create(bookDto);
        if (Optional.ofNullable(createdBook).isPresent()) {
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<BookDto> updateBook(@Valid @RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.update(bookDto);
        if (Optional.ofNullable(updatedBook).isPresent()) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@Valid @Positive @PathVariable Long id) {
        boolean isDelete = bookService.delete(id);
        if (isDelete) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{title}")
    public ResponseEntity<BookDto> getBookByTitle(@PathVariable @NotBlank String title) {
        BookDto bookDTO = bookService.findBookByTitle(title);
        if (Optional.ofNullable(bookDTO).isPresent()) {
            return new ResponseEntity<>(bookDTO, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/{date}")
    public ResponseEntity<List<BookDto>> getAllBooksByDate(@PathVariable @NotBlank String date) {
        List<BookDto> booksDTO = bookService.findBooksByDate(date);
        if (isNotEmpty(booksDTO)) {
            return new ResponseEntity<>(booksDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
