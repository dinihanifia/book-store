package com.test.bookstore.POJO.Response;
import com.test.bookstore.POJO.Entity.Books;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetAllBooksResponse {
    private int responseCode;
    private String responseMessage;
    private List<Books> books;
}
