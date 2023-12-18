package com.test.bookstore.Repository;
import com.test.bookstore.POJO.Entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import javax.transaction.Transactional;

@Repository
public interface BookRepository extends JpaRepository<Books, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE Books b SET b.bookStock = 110 WHERE b.bookId = :bookId")
    void updateBookStock(@Param("bookId") UUID bookId);
}
