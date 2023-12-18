package com.test.bookstore.Repository;
import com.test.bookstore.POJO.Entity.Publishers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PublisherRepository extends JpaRepository<Publishers, UUID> {
}
