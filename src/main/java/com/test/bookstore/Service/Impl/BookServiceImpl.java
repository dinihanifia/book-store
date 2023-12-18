package com.test.bookstore.Service.Impl;
import com.test.bookstore.Config.MessageConstant;
import com.test.bookstore.POJO.Entity.Books;
import com.test.bookstore.POJO.Entity.ECategories;
import com.test.bookstore.POJO.Entity.Publishers;
import com.test.bookstore.POJO.Request.BooksRequest;
import com.test.bookstore.POJO.Response.BooksResponse;
import com.test.bookstore.POJO.Response.GetAllBooksResponse;
import com.test.bookstore.Repository.BookRepository;
import com.test.bookstore.Repository.PublisherRepository;
import com.test.bookstore.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Cacheable;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public GetAllBooksResponse getAllBooks() {
        GetAllBooksResponse response = new GetAllBooksResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            var checkBooks = bookRepository.findAll();
            checkBooks.stream().filter(Objects::nonNull)
                    .map(books -> {
                        if (books==null){
                            return GetAllBooksResponse.builder()
                                    .responseCode(HttpStatus.NOT_FOUND.value())
                                    .responseMessage(MessageConstant.NOT_FOUND_MESSAGE)
                                    .build();
                        }
                        return books;
                    })
                    .collect(Collectors.toList());
            return GetAllBooksResponse.builder()
                    .responseCode(HttpStatus.OK.value())
                    .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                    .books(checkBooks)
                    .build();
        } catch (Exception e){
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    public BooksResponse getBooks(UUID bookId) {
        BooksResponse response = new BooksResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            Optional<Books> checkBooks = bookRepository.findById(bookId);
            checkBooks.ifPresentOrElse(
                    books -> {
                        response.setResponseCode(HttpStatus.OK.value());
                        response.setResponseMessage(MessageConstant.SUCCESS_MESSAGE);
                        response.setBooks(books);
                    },
                    () -> {
                        response.setResponseCode(HttpStatus.NOT_FOUND.value());
                        response.setResponseMessage(MessageConstant.NOT_FOUND_MESSAGE);
                    }
            );
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    public BooksResponse saveBooks(BooksRequest request) {
        BooksResponse response = new BooksResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            Optional<Publishers> checkPublishers = publisherRepository.findById(request.getPublishers().getPublisherId());
            checkPublishers.ifPresentOrElse(
                    publishers -> {
                        var getPublishers = checkPublishers.get();
                        Books books = Books.builder()
                                .bookTitle(request.getBookTitle())
                                .categories(request.getCategories())
                                .publishers(getPublishers)
                                .datePublished(request.getDatePublished())
                                .bookPrice(request.getBookPrice())
                                .bookStock(request.getBookStock())
                                .build();
                        var save = bookRepository.save(books);
                        response.setResponseCode(HttpStatus.OK.value());
                        response.setResponseMessage(MessageConstant.SUCCESS_MESSAGE);
                        response.setBooks(save);
                    },
                    () -> {
                        response.setResponseCode(HttpStatus.NOT_FOUND.value());
                        response.setResponseMessage(MessageConstant.NOT_FOUND_MESSAGE);
                    }
            );
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    public BooksResponse updateBooks(UUID bookId, BooksRequest request) {
        BooksResponse response = new BooksResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            var checkBooksId = getBooks(bookId);
            if(checkBooksId.getResponseCode()==HttpStatus.OK.value()){
                var updateBooks = checkBooksId.getBooks();
                updateBooks.setBookTitle(request.getBookTitle());
                updateBooks.setCategories(request.getCategories());
                updateBooks.setPublishers(request.getPublishers());
                updateBooks.setDatePublished(request.getDatePublished());
                updateBooks.setBookPrice(request.getBookPrice());
                updateBooks.setBookStock(request.getBookStock());
                bookRepository.save(updateBooks);
                return BooksResponse.builder()
                        .responseCode(HttpStatus.OK.value())
                        .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                        .books(updateBooks)
                        .build();
            }
            return BooksResponse.builder()
                    .responseCode(HttpStatus.NOT_FOUND.value())
                    .responseMessage(MessageConstant.NOT_FOUND_MESSAGE)
                    .build();
        } catch (Exception e){
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    public BooksResponse deleteBooks(UUID bookId) {
        BooksResponse response = new BooksResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            var checkBooksId = getBooks(bookId);
            if(checkBooksId.getResponseCode()==HttpStatus.OK.value()){
                bookRepository.deleteById(bookId);
                return BooksResponse.builder()
                        .responseCode(HttpStatus.OK.value())
                        .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                        .build();
            }
            return BooksResponse.builder()
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
