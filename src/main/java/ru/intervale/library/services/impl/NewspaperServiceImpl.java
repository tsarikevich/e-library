package ru.intervale.library.services.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.intervale.library.dto.NewspaperDto;
import ru.intervale.library.dto.converters.NewspaperConverter;
import ru.intervale.library.entities.Newspaper;
import ru.intervale.library.repositiries.AuthorRepository;
import ru.intervale.library.repositiries.NewspaperRepository;
import ru.intervale.library.repositiries.PublisherRepository;
import ru.intervale.library.services.NewspaperService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;


@Service
@Log4j
public class NewspaperServiceImpl extends PrintedProductBaseService implements NewspaperService {
    private final NewspaperRepository newspaperRepository;
    private final NewspaperConverter newspaperConverter;

    public NewspaperServiceImpl(AuthorRepository authorRepository, PublisherRepository publisherRepository, NewspaperRepository newspaperRepository, NewspaperConverter newspaperConverter) {
        super(authorRepository, publisherRepository);
        this.newspaperRepository = newspaperRepository;
        this.newspaperConverter = newspaperConverter;
    }

    @Override
    public NewspaperDto getNewspaperData(long id) {
        Optional<Newspaper> newspaper = newspaperRepository.findById(id);
        if (newspaper.isPresent()) {
            return newspaperConverter.toDto(newspaper.get());
        } else {
            log.warn("The newspaper wasn't found with id = " + id);
            return null;
        }
    }

    @Override
    public List<NewspaperDto> getAllNewspapersData() {
        List<Newspaper> newspapers = newspaperRepository.findAll();
        return newspapers.stream().map(newspaperConverter::toDto).toList();
    }

    @Override
    public NewspaperDto create(NewspaperDto newspaperDto) {
        try {
            Newspaper newspaper = newspaperConverter.fromDto(newspaperDto);
            if (Optional.ofNullable(newspaper.getTitle()).isEmpty()) {
                log.warn("The newspaper can't be created because title is null");
            } else {
                Newspaper createdNewspaper = newspaperRepository.save(newspaper);
                updatePrintedProduct(createdNewspaper);
                return newspaperConverter.toDto(createdNewspaper);
            }
        } catch (Exception e) {
            log.error("The newspaper wasn't created: ", e);
        }
        return null;
    }

    @Override
    public NewspaperDto update(NewspaperDto newspaperDto) {
        try {
            Newspaper newspaper = newspaperConverter.fromDto(newspaperDto);
            if (newspaperRepository.existsById(newspaper.getId())) {
                Newspaper updatedNewspaper = newspaperRepository.save(newspaper);
                return newspaperConverter.toDto(updatedNewspaper);
            } else {
                log.warn("The newspaper can't be updated because it doesn't exist in the DB");
            }
        } catch (Exception e) {
            log.error("The newspaper wasn't updated: ", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            newspaperRepository.deleteById(id);
        } catch (Exception e) {
            log.error("The newspaper wasn't deleted: ", e);
            return false;
        }
        return true;
    }

    @Override
    public NewspaperDto findNewspaperByTitle(String title) {
        Optional<Newspaper> newspaper = newspaperRepository.findNewspaperByTitle(title);
        return newspaper.map(newspaperConverter::toDto).orElse(null);
    }

    @Override
    public List<NewspaperDto> findNewspapersByDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Newspaper> newspapers = newspaperRepository.findAllByDate(localDate);
            return newspapers.stream().map(newspaperConverter::toDto).toList();
        } catch (Exception e) {
            log.error("The newspaper wasn't got by date: ", e);
        }
        return emptyList();
    }
}
