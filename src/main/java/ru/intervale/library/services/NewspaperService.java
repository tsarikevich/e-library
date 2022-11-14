package ru.intervale.library.services;

import ru.intervale.library.dto.NewspaperDto;

import java.util.List;

public interface NewspaperService extends BaseService<NewspaperDto> {
    NewspaperDto getNewspaperData(long id);

    List<NewspaperDto> getAllNewspapersData();

    NewspaperDto findNewspaperByTitle(String title);

    List<NewspaperDto> findNewspapersByDate(String date);
}
