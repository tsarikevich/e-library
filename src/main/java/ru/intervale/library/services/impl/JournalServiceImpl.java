package ru.intervale.library.services.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.intervale.library.dto.JournalDto;
import ru.intervale.library.dto.converters.JournalConverter;
import ru.intervale.library.entities.Journal;
import ru.intervale.library.repositiries.AuthorRepository;
import ru.intervale.library.repositiries.JournalRepository;
import ru.intervale.library.repositiries.PublisherRepository;
import ru.intervale.library.services.JournalService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@Log4j
public class JournalServiceImpl extends PrintedProductBaseService implements JournalService {
    private final JournalRepository journalRepository;
    private final JournalConverter journalConverter;

    public JournalServiceImpl(AuthorRepository authorRepository, PublisherRepository publisherRepository, JournalRepository journalRepository, JournalConverter journalConverter) {
        super(authorRepository, publisherRepository);
        this.journalRepository = journalRepository;
        this.journalConverter = journalConverter;
    }

    @Override
    public JournalDto getJournalData(long id) {
        Optional<Journal> journal = journalRepository.findById(id);
        if (journal.isPresent()) {
            return journalConverter.toDto(journal.get());
        } else {
            log.warn("The journal wasn't found with id = " + id);
            return null;
        }
    }

    @Override
    public List<JournalDto> getAllJournalsData() {
        List<Journal> journals = journalRepository.findAll();
        return journals.stream().map(journalConverter::toDto).toList();
    }

    @Override
    public JournalDto create(JournalDto journalDto) {
        try {
            Journal journal = journalConverter.fromDto(journalDto);
            if (Optional.ofNullable(journal.getTitle()).isEmpty()) {
                log.warn("The journal can't be created because title is null");
            } else {
                Journal createdJournal = journalRepository.save(journal);
                updatePrintedProduct(createdJournal);
                return journalConverter.toDto(createdJournal);
            }
        } catch (Exception e) {
            log.error("The journal wasn't created: ", e);

        }
        return null;
    }

    @Override
    public JournalDto update(JournalDto journalDto) {
        try {
            Journal journal = journalConverter.fromDto(journalDto);
            if (journalRepository.existsById(journal.getId())) {
                Journal updatedJournal = journalRepository.save(journal);
                return journalConverter.toDto(updatedJournal);
            } else {
                log.warn("The journal can't be updated because it doesn't exist in the DB");
            }
        } catch (Exception e) {
            log.error("The journal wasn't updated: ", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            journalRepository.deleteById(id);
        } catch (Exception e) {
            log.error("The journal wasn't deleted: ", e);
            return false;
        }
        return true;
    }

    @Override
    public JournalDto findJournalByTitle(String title) {
        Optional<Journal> journal = journalRepository.findJournalByTitle(title);
        return journal.map(journalConverter::toDto).orElse(null);
    }

    @Override
    public List<JournalDto> findJournalsByDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Journal> journals = journalRepository.findAllByDate(localDate);
            return journals.stream().map(journalConverter::toDto).toList();
        } catch (Exception e) {
            log.error("The journal wasn't got by date: ", e);
        }
        return emptyList();
    }
}
