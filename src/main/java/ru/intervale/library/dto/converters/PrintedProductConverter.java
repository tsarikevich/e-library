package ru.intervale.library.dto.converters;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import ru.intervale.library.dto.PrintedProductDto;
import ru.intervale.library.entities.Book;
import ru.intervale.library.entities.Journal;
import ru.intervale.library.entities.Newspaper;
import ru.intervale.library.entities.PrintedProduct;

@Component
@Log4j
@RequiredArgsConstructor
public class PrintedProductConverter {
    private final BookConverter bookConverter;
    private final JournalConverter journalConverter;
    private final NewspaperConverter newspaperConverter;

    public PrintedProductDto toDto(PrintedProduct product) {
        if (product instanceof Book) {
            return bookConverter.toDto((Book) product);
        } else if (product instanceof Journal) {
            return journalConverter.toDto((Journal) product);
        } else if (product instanceof Newspaper) {
            return newspaperConverter.toDto((Newspaper) product);
        }
        return new PrintedProductDto();
    }
}
