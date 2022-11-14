package ru.intervale.library.services;

import ru.intervale.library.dto.PrintedProductDto;

import java.util.List;
import java.util.Map;

public interface LibraryService {
    Map<String, List<PrintedProductDto>> findProductsByDate();

    List<PrintedProductDto> getAllLibraryData();
}
