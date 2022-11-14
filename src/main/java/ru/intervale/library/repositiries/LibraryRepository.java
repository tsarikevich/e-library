package ru.intervale.library.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.intervale.library.entities.PrintedProduct;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<PrintedProduct, Long> {
    List<PrintedProduct> findAllByPublisherId(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM LIBRARY.PRINTED_PRODUCTS P JOIN LIBRARY.PRODUCTS_AUTHORS PA ON P.ID=PA.PRODUCT_ID JOIN LIBRARY.AUTHORS A ON A.ID=PA.AUTHOR_ID WHERE A.ID=?1")
    List<PrintedProduct> findByAuthorsId(Long id);
}
