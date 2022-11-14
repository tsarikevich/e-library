package ru.intervale.library.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.intervale.library.dto.PrintedProductDto;
import ru.intervale.library.dto.PublisherDto;
import ru.intervale.library.dto.converters.PrintedProductConverter;
import ru.intervale.library.dto.converters.PublisherConverter;
import ru.intervale.library.entities.PrintedProduct;
import ru.intervale.library.entities.Publisher;
import ru.intervale.library.repositiries.LibraryRepository;
import ru.intervale.library.repositiries.PublisherRepository;
import ru.intervale.library.services.PublisherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherConverter publisherConverter;
    private final PrintedProductConverter printedProductConverter;
    private final LibraryRepository libraryRepository;

    @Override
    public Map<PublisherDto, List<PrintedProductDto>> getPublisherData(long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        Map<PublisherDto, List<PrintedProductDto>> publisherDtoMap = new HashMap<>();
        if (publisher.isPresent()) {
            convertPublisherToMap(publisher, publisherDtoMap);
        } else {
            log.warn("The publisher wasn't found with id = " + id);
        }
        return publisherDtoMap;
    }

    @Override
    public Map<PublisherDto, List<PrintedProductDto>> getAllPublishersData() {
        List<Publisher> publishers = publisherRepository.findAll();
        return publishers.stream()
                .collect(Collectors.toMap(publisherConverter::toDto,
                        publisher -> publisher.getPrintedProducts()
                                .stream()
                                .map(printedProductConverter::toDto)
                                .toList()
                ));
    }

    @Override
    public PublisherDto create(PublisherDto publisherDto) {
        try {
            Publisher publisher = publisherConverter.fromDto(publisherDto);
            if (Optional.ofNullable(publisher.getName()).isEmpty()) {
                log.warn("The publisher can't be created because name is null");
            } else {
                Publisher createdPublisher = publisherRepository.save(publisher);
                return publisherConverter.toDto(createdPublisher);
            }
        } catch (Exception e) {
            log.error("The publisher wasn't created: ", e);
        }
        return null;
    }

    @Override
    public PublisherDto update(PublisherDto publisherDto) {
        try {
            Publisher publisher = publisherConverter.fromDto(publisherDto);
            if (publisherRepository.existsById(publisher.getId())) {
                List<PrintedProduct> printedProducts = libraryRepository.findAllByPublisherId(publisherDto.getId());
                publisher.setPrintedProducts(printedProducts);
                Publisher updatedPublisher = publisherRepository.save(publisher);
                return publisherConverter.toDto(updatedPublisher);
            } else {
                log.warn("The publisher can't be updated because it doesn't exist in the DB");
            }
        } catch (Exception e) {
            log.error("The publisher wasn't updated: ", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            publisherRepository.deleteById(id);
        } catch (Exception e) {
            log.error("The publisher wasn't deleted: ", e);
            return false;
        }
        return true;
    }

    @Override
    public Map<PublisherDto, List<PrintedProductDto>> findPublisherByName(String name) {
        Optional<Publisher> publisher = publisherRepository.findPublisherByName(name);
        Map<PublisherDto, List<PrintedProductDto>> publisherDtoMap = new HashMap<>();
        if (publisher.isPresent()) {
            convertPublisherToMap(publisher, publisherDtoMap);
        } else {
            log.warn("The publisher wasn't found by name = " + publisherDtoMap);
        }
        return publisherDtoMap;
    }

    private void convertPublisherToMap(Optional<Publisher> publisher, Map<PublisherDto, List<PrintedProductDto>> publishersDtoMap) {
        publishersDtoMap.put(publisherConverter.toDto(publisher.get()),
                publisher.get()
                        .getPrintedProducts()
                        .stream()
                        .map(printedProductConverter::toDto)
                        .toList());
    }
}
