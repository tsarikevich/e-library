package ru.intervale.library.services.impl;

import ru.intervale.library.entities.Author;
import ru.intervale.library.entities.PrintedProduct;
import ru.intervale.library.entities.Publisher;
import ru.intervale.library.repositiries.AuthorRepository;
import ru.intervale.library.repositiries.PublisherRepository;

import java.util.Optional;
import java.util.Set;

public class PrintedProductBaseService {
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public PrintedProductBaseService(AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    protected void updatePrintedProduct(PrintedProduct product) {
        Set<Author> authors = authorRepository.findByIdIn(
                product.getAuthors()
                        .stream()
                        .map(Author::getId)
                        .toList());
        product.setAuthors(authors);
        Optional<Publisher> publisher = publisherRepository.findById(product.getPublisher().getId());
        product.setPublisher(publisher.orElse(new Publisher()));
    }
}

