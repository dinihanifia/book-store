package com.test.bookstore.Service;
import com.test.bookstore.POJO.Request.PublisherRequest;
import com.test.bookstore.POJO.Response.GetAllPublisherResponse;
import com.test.bookstore.POJO.Response.PublisherResponse;

import java.util.UUID;

public interface PublisherService {
    GetAllPublisherResponse getAllPublisher();
    PublisherResponse getOnePublisher(UUID publisherId);
    PublisherResponse savePublisher(PublisherRequest request);
    PublisherResponse updatePublisher(UUID publisherId, PublisherRequest request);
    PublisherResponse deletePublisher(UUID publisherId);
}
