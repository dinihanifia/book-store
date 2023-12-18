package com.test.bookstore.POJO.Response;
import com.test.bookstore.POJO.Entity.Publishers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PublisherResponse {
    private int responseCode;
    private String responseMessage;
    private Publishers publisher;
}
