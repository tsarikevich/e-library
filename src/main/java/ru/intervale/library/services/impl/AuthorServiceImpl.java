package ru.intervale.library.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.intervale.library.dto.AuthorDto;
import ru.intervale.library.dto.PrintedProductDto;
import ru.intervale.library.dto.converters.AuthorConverter;
import ru.intervale.library.dto.converters.PrintedProductConverter;
import ru.intervale.library.entities.Author;
import ru.intervale.library.entities.PrintedProduct;
import ru.intervale.library.repositiries.AuthorRepository;
import ru.intervale.library.repositiries.LibraryRepository;
import ru.intervale.library.services.AuthorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;
    private final PrintedProductConverter printedProductConverter;
    private final LibraryRepository libraryRepository;

    @Override
    public Map<AuthorDto, List<PrintedProductDto>> getAuthorData(long id) {
        Optional<Author> author = authorRepository.findById(id);
        Map<AuthorDto, List<PrintedProductDto>> authorDtoMap = new HashMap<>();
        if (author.isPresent()) {
            convertAuthorToMap(author, authorDtoMap);
        } else {
            log.warn("The author wasn't found with id = " + id);
        }
        return authorDtoMap;
    }

    @Override
    public Map<AuthorDto, List<PrintedProductDto>> getAllAuthorsData() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .collect(Collectors.toMap(authorConverter::toDto,
                        author -> author.getPrintedProducts()
                                .stream()
                                .map(printedProductConverter::toDto)
                                .toList()
                ));
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        try {
            Author author = authorConverter.fromDto(authorDto);
            if (Optional.ofNullable(author.getLastName()).isEmpty()) {
                log.warn("The author can't be created because last name is null");
            } else {
                Author createdAuthor = authorRepository.save(author);
                return authorConverter.toDto(createdAuthor);
            }
        } catch (Exception e) {
            log.error("The author wasn't created: ", e);
        }
        return null;
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        try {
            Author author = authorConverter.fromDto(authorDto);
            if (authorRepository.existsById(author.getId())) {
                List<PrintedProduct> printedProducts = libraryRepository.findByAuthorsId(authorDto.getId());
                author.setPrintedProducts(printedProducts);
                Author updatedAuthor = authorRepository.save(author);
                return authorConverter.toDto(updatedAuthor);
            } else {
                log.warn("The author can't be updated because it doesn't exist in the DB");
            }
        } catch (Exception e) {
            log.error("The author wasn't updated: ", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            authorRepository.deleteById(id);
        } catch (Exception e) {
            log.error("The author wasn't deleted: ", e);
            return false;
        }
        return true;
    }

    @Override
    public Map<AuthorDto, List<PrintedProductDto>> findAuthorsByLastName(String lastName) {
        Optional<Author> author = authorRepository.findAuthorByLastName(lastName);
        Map<AuthorDto, List<PrintedProductDto>> authorDtoMap = new HashMap<>();
        if (author.isPresent()) {
            convertAuthorToMap(author, authorDtoMap);
        } else {
            log.warn("The author wasn't found by last name = ");
        }
        return authorDtoMap;
    }

    private void convertAuthorToMap(Optional<Author> author, Map<AuthorDto, List<PrintedProductDto>> authorsDtoMap) {
        authorsDtoMap.put(authorConverter.toDto(author.get()),
                author.get()
                        .getPrintedProducts()
                        .stream()
                        .map(printedProductConverter::toDto)
                        .toList());
    }
}
