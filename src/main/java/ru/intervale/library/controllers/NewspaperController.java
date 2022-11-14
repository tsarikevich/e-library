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
import ru.intervale.library.dto.NewspaperDto;
import ru.intervale.library.services.NewspaperService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Validated
@RestController
@RequestMapping("/newspapers")
@Log4j
public class NewspaperController {
    private final NewspaperService newspaperService;

    public NewspaperController(NewspaperService newspaperService) {
        this.newspaperService = newspaperService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewspaperDto> getNewspaperData(@Valid @Positive @PathVariable Long id) {
        NewspaperDto newspaperDto = newspaperService.getNewspaperData(id);
        if (Optional.ofNullable(newspaperDto).isPresent()) {
            return new ResponseEntity<>(newspaperDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewspaperDto>> getAllNewspapersData() {
        List<NewspaperDto> newspapersDto = newspaperService.getAllNewspapersData();
        if (isNotEmpty(newspapersDto)) {
            return new ResponseEntity<>(newspapersDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<NewspaperDto> createNewspaper(@RequestBody NewspaperDto newspaperDto) {
        NewspaperDto createdNewspaper = newspaperService.create(newspaperDto);
        if (Optional.ofNullable(createdNewspaper).isPresent()) {
            return new ResponseEntity<>(createdNewspaper, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<NewspaperDto> updateNewspaper(@Valid @RequestBody NewspaperDto newspaperDto) {
        NewspaperDto updatedNewspaper = newspaperService.update(newspaperDto);
        if (Optional.ofNullable(updatedNewspaper).isPresent()) {
            return new ResponseEntity<>(updatedNewspaper, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteNewspaper(@Valid @Positive @PathVariable Long id) {
        boolean isDelete = newspaperService.delete(id);
        if (isDelete) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{title}")
    public ResponseEntity<NewspaperDto> getNewspaperByTitle(@PathVariable @NotBlank String title) {
        NewspaperDto newspaperDto = newspaperService.findNewspaperByTitle(title);
        if (Optional.ofNullable(newspaperDto).isPresent()) {
            return new ResponseEntity<>(newspaperDto, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/{date}")
    public ResponseEntity<List<NewspaperDto>> getAllNewspapersByDate(@PathVariable @NotBlank String date) {
        List<NewspaperDto> newspapersDto = newspaperService.findNewspapersByDate(date);
        if (isNotEmpty(newspapersDto)) {
            return new ResponseEntity<>(newspapersDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
