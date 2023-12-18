package com.test.bookstore.Controller;
import com.test.bookstore.Config.URLConstant;
import com.test.bookstore.POJO.Request.PublisherRequest;
import com.test.bookstore.POJO.Response.GetAllPublisherResponse;
import com.test.bookstore.POJO.Response.PublisherResponse;
import com.test.bookstore.Service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(URLConstant.PUBLISHER_URL)
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping(URLConstant.GET_ALL_URL)
    public GetAllPublisherResponse getAllPublisher(){
        return publisherService.getAllPublisher();
    }

    @GetMapping(URLConstant.GET_ONE_URL)
    public PublisherResponse getOnePublisher(@RequestParam("publisherId")UUID publisherId){
        return publisherService.getOnePublisher(publisherId);
    }

    @PostMapping(URLConstant.SAVE_URL)
    public PublisherResponse savePublisher(@RequestBody PublisherRequest request){
        return publisherService.savePublisher(request);
    }
    @PutMapping(URLConstant.UPDATE_URL)
    public PublisherResponse updatePublisher(@RequestParam("publisherId")UUID publisherId, @RequestBody PublisherRequest request){
        return publisherService.updatePublisher(publisherId, request);
    }
    @DeleteMapping(URLConstant.DELETE_URL)
    public PublisherResponse deletePublisher(@RequestParam("publisherId")UUID publisherId){
        return publisherService.deletePublisher(publisherId);
    }
}
