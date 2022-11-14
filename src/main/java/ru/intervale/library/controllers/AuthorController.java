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
import ru.intervale.library.dto.AuthorDto;
import ru.intervale.library.dto.PrintedProductDto;
import ru.intervale.library.services.AuthorService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.collections4.MapUtils.isNotEmpty;

@Validated
@RestController
@RequestMapping("/authors")
@Log4j
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<AuthorDto, List<PrintedProductDto>>> getBookData(@Valid @Positive @PathVariable Long id) {
        Map<AuthorDto, List<PrintedProductDto>> author = authorService.getAuthorData(id);
        if (isNotEmpty(author)) {
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<AuthorDto, List<PrintedProductDto>>> getAllAuthorsData() {
        Map<AuthorDto, List<PrintedProductDto>> authors = authorService.getAllAuthorsData();
        if (isNotEmpty(authors)) {
            return new ResponseEntity<>(authors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorDto createdAuthor = authorService.create(authorDto);
        if (Optional.ofNullable(createdAuthor).isPresent()) {
            return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<AuthorDto> updateAuthor(@Valid @RequestBody AuthorDto authorDto) {
        AuthorDto updatedAuthor = authorService.update(authorDto);
        if (Optional.ofNullable(updatedAuthor).isPresent()) {
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAuthor(@Valid @Positive @PathVariable Long id) {
        boolean isDelete = authorService.delete(id);
        if (isDelete) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{lastName}")
    public ResponseEntity<Map<AuthorDto, List<PrintedProductDto>>> getAuthorByLastName(@PathVariable @NotBlank String lastName) {
        Map<AuthorDto, List<PrintedProductDto>> authorsDto = authorService.findAuthorsByLastName(lastName);
        if (isNotEmpty(authorsDto)) {
            return new ResponseEntity<>(authorsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
