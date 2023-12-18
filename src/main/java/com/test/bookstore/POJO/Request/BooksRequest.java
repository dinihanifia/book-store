package com.test.bookstore.POJO.Request;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.bookstore.POJO.Entity.ECategories;
import com.test.bookstore.POJO.Entity.Publishers;
import com.test.bookstore.POJO.Entity.Transactions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BooksRequest {
    private String bookTitle;
    @JsonProperty("categories")
    private ECategories categories;
    @JsonProperty("publishers")
    private Publishers publishers;
    private String datePublished;
    private Long bookPrice;
    private Long bookStock;
}
