package com.test.bookstore.Service.Impl;
import com.test.bookstore.Config.MessageConstant;
import com.test.bookstore.POJO.Entity.Publishers;
import com.test.bookstore.POJO.Request.PublisherRequest;
import com.test.bookstore.POJO.Response.GetAllPublisherResponse;
import com.test.bookstore.POJO.Response.PublisherResponse;
import com.test.bookstore.Repository.PublisherRepository;
import com.test.bookstore.Service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public GetAllPublisherResponse getAllPublisher() {
        GetAllPublisherResponse response = new GetAllPublisherResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try{
            var checkPublishers = publisherRepository.findAll();
            List<Publishers> publishersList = checkPublishers.stream().collect(Collectors.toList());
            return GetAllPublisherResponse.builder()
                    .responseCode(HttpStatus.OK.value())
                    .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                    .publisher(publishersList)
                    .build();
        } catch (Exception e){
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    public PublisherResponse getOnePublisher(UUID publisherId) {
        PublisherResponse response = new PublisherResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try{
            Optional<Publishers> checkPublishers = publisherRepository.findById(publisherId);
            checkPublishers.ifPresentOrElse(
                    publishers -> {
                        response.setResponseCode(HttpStatus.OK.value());
                        response.setResponseMessage(MessageConstant.SUCCESS_MESSAGE);
                        response.setPublisher(publishers);
                    },
                    () -> {
                        response.setResponseCode(HttpStatus.NOT_FOUND.value());
                        response.setResponseMessage(MessageConstant.NOT_FOUND_MESSAGE);
                    }
            );
        } catch (Exception e){
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    public PublisherResponse savePublisher(PublisherRequest request) {
        PublisherResponse response = new PublisherResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try{
            Publishers publishers = new Publishers();
            publishers.setPublisherName(request.getPublisherName());
            publishers.setPublisherAddress(request.getPublisherAddress());
            publisherRepository.save(publishers);
            return PublisherResponse.builder()
                    .responseCode(HttpStatus.OK.value())
                    .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                    .publisher(publishers)
                    .build();
        } catch (Exception e){
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    public PublisherResponse updatePublisher(UUID publisherId, PublisherRequest request) {
        PublisherResponse response = new PublisherResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try{
            var checkPublisher = getOnePublisher(publisherId);
            if(checkPublisher.getResponseCode() == HttpStatus.OK.value()){
                var updatePublisher = checkPublisher.getPublisher();
                updatePublisher.setPublisherName(request.getPublisherName());
                updatePublisher.setPublisherAddress(request.getPublisherAddress());
                publisherRepository.save(updatePublisher);
                return PublisherResponse.builder()
                        .responseCode(HttpStatus.OK.value())
                        .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                        .publisher(updatePublisher)
                        .build();
            }
            return PublisherResponse.builder()
                    .responseCode(HttpStatus.NOT_FOUND.value())
                    .responseMessage(MessageConstant.NOT_FOUND_MESSAGE)
                    .build();
        } catch(Exception e){
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    public PublisherResponse deletePublisher(UUID publisherId) {
        PublisherResponse response = new PublisherResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            var checkPublisher = getOnePublisher(publisherId);
            if(checkPublisher.getResponseCode() == HttpStatus.OK.value()){
                return PublisherResponse.builder()
                        .responseCode(HttpStatus.OK.value())
                        .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                        .build();
            }
            return PublisherResponse.builder()
                    .responseCode(HttpStatus.NOT_FOUND.value())
                    .responseMessage(MessageConstant.NOT_FOUND_MESSAGE)
                    .build();
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }
}
