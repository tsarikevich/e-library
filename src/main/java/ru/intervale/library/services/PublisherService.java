package ru.intervale.library.services;

import ru.intervale.library.dto.PrintedProductDto;
import ru.intervale.library.dto.PublisherDto;

import java.util.List;
import java.util.Map;

public interface PublisherService extends BaseService<PublisherDto> {
    Map<PublisherDto, List<PrintedProductDto>> getPublisherData(long id);

    Map<PublisherDto, List<PrintedProductDto>> getAllPublishersData();

    Map<PublisherDto, List<PrintedProductDto>> findPublisherByName(String name);
}
