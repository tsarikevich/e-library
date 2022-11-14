package ru.intervale.library.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.intervale.library.entities.Newspaper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Long> {
    Optional<Newspaper> findNewspaperByTitle(String title);

    List<Newspaper> findAllByDate(LocalDate date);
}
