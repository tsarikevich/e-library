package ru.intervale.library.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.library.dto.PrintedProductDto;
import ru.intervale.library.services.LibraryService;

import java.util.List;
import java.util.Map;

import static org.apache.commons.collections4.MapUtils.isNotEmpty;

@RestController
@RequestMapping("/products")
@Log4j
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/find/date")
    public ResponseEntity<Map<String, List<PrintedProductDto>>> getAllProductsByDate() {
        Map<String, List<PrintedProductDto>> products = libraryService.findProductsByDate();
        if (isNotEmpty(products)) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PrintedProductDto>> getAllLibraryData() {
        List<PrintedProductDto> products = libraryService.getAllLibraryData();
        if (CollectionUtils.isNotEmpty(products)) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
