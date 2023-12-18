package com.test.bookstore.POJO.Request;
import com.test.bookstore.POJO.Entity.Books;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionRequest {
    private Books books;
    private Long quantity;
    private String createdBy;
}
