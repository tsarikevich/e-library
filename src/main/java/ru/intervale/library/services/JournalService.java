package ru.intervale.library.services;

import ru.intervale.library.dto.JournalDto;

import java.util.List;

public interface JournalService extends BaseService<JournalDto> {
    JournalDto getJournalData(long id);

    List<JournalDto> getAllJournalsData();

    JournalDto findJournalByTitle(String title);

    List<JournalDto> findJournalsByDate(String date);
}
