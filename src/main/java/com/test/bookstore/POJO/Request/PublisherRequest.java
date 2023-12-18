package com.test.bookstore.POJO.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PublisherRequest {
    private String publisherName;
    private String publisherAddress;
}
