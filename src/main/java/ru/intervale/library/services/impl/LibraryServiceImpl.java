package ru.intervale.library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.intervale.library.dto.PrintedProductDto;
import ru.intervale.library.dto.converters.PrintedProductConverter;
import ru.intervale.library.entities.PrintedProduct;
import ru.intervale.library.repositiries.LibraryRepository;
import ru.intervale.library.services.LibraryService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final PrintedProductConverter printedProductConverter;

    @Override
    public Map<String, List<PrintedProductDto>> findProductsByDate() {
        List<PrintedProduct> printedProducts = libraryRepository.findAll();
        return printedProducts
                .stream()
                .collect(Collectors.groupingBy(PrintedProduct::getDate))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(k -> k.getKey().toString(),
                        v -> v.getValue()
                                .stream()
                                .map(printedProductConverter::toDto).toList()));
    }

    @Override
    public List<PrintedProductDto> getAllLibraryData() {
        return libraryRepository.findAll()
                .stream()
                .map(printedProductConverter::toDto)
                .toList();
    }
}
