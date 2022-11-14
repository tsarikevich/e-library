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
import ru.intervale.library.dto.PrintedProductDto;
import ru.intervale.library.dto.PublisherDto;
import ru.intervale.library.services.PublisherService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.collections4.MapUtils.isNotEmpty;

@Validated
@RestController
@RequestMapping("/publishers")
@Log4j
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<PublisherDto, List<PrintedProductDto>>> getPublisherData(@Valid @Positive @PathVariable long id) {
        Map<PublisherDto, List<PrintedProductDto>> publisher = publisherService.getPublisherData(id);
        if (isNotEmpty(publisher)) {
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<PublisherDto, List<PrintedProductDto>>> getAllPublishersData() {
        Map<PublisherDto, List<PrintedProductDto>> publishers = publisherService.getAllPublishersData();
        if (isNotEmpty(publishers)) {
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PublisherDto> createPublisher(@RequestBody PublisherDto publisherDto) {
        PublisherDto createdPublisher = publisherService.create(publisherDto);
        if (Optional.ofNullable(createdPublisher).isPresent()) {
            return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<PublisherDto> updatePublisher(@Valid @RequestBody PublisherDto publisherDto) {
        PublisherDto updatedPublisher = publisherService.update(publisherDto);
        if (Optional.ofNullable(updatedPublisher).isPresent()) {
            return new ResponseEntity<>(updatedPublisher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePublisher(@Valid @Positive @PathVariable Long id) {
        boolean isDelete = publisherService.delete(id);
        if (isDelete) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Map<PublisherDto, List<PrintedProductDto>>> getPublisherByName(@PathVariable @NotBlank String name) {
        Map<PublisherDto, List<PrintedProductDto>> publishersDto = publisherService.findPublisherByName(name);
        if (isNotEmpty(publishersDto)) {
            return new ResponseEntity<>(publishersDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
