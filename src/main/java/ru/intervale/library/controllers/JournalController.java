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
import ru.intervale.library.dto.JournalDto;
import ru.intervale.library.services.JournalService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Validated
@RestController
@RequestMapping("/journals")
@Log4j
public class JournalController {
    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalDto> getJournalData(@Valid @Positive @PathVariable Long id) {
        JournalDto journalDto = journalService.getJournalData(id);
        if (Optional.ofNullable(journalDto).isPresent()) {
            return new ResponseEntity<>(journalDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<JournalDto>> getAllJournalsData() {
        List<JournalDto> journalsDto = journalService.getAllJournalsData();
        if (isNotEmpty(journalsDto)) {
            return new ResponseEntity<>(journalsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalDto> createJournal(@RequestBody JournalDto journalDto) {
        JournalDto createdJournal = journalService.create(journalDto);
        if (Optional.ofNullable(createdJournal).isPresent()) {
            return new ResponseEntity<>(createdJournal, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<JournalDto> updateJournal(@Valid @RequestBody JournalDto journalDto) {
        JournalDto updatedJournal = journalService.update(journalDto);
        if (Optional.ofNullable(updatedJournal).isPresent()) {
            return new ResponseEntity<>(updatedJournal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteJournal(@Valid @Positive @PathVariable Long id) {
        boolean isDelete = journalService.delete(id);
        if (isDelete) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{title}")
    public ResponseEntity<JournalDto> getJournalByTitle(@PathVariable @NotBlank String title) {
        JournalDto journalDto = journalService.findJournalByTitle(title);
        if (Optional.ofNullable(journalDto).isPresent()) {
            return new ResponseEntity<>(journalDto, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/{date}")
    public ResponseEntity<List<JournalDto>> getAllJournalsByDate(@PathVariable String date) {
        List<JournalDto> journalsDto = journalService.findJournalsByDate(date);
        if (isNotEmpty(journalsDto)) {
            return new ResponseEntity<>(journalsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
