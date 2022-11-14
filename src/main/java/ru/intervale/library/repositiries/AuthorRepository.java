package ru.intervale.library.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.intervale.library.entities.Author;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByLastName(String lastName);

    Set<Author> findByIdIn(List<Long> authorsId);
}

