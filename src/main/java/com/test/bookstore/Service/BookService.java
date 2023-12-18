package com.test.bookstore.Service;
import com.test.bookstore.POJO.Request.BooksRequest;
import com.test.bookstore.POJO.Response.BooksResponse;
import com.test.bookstore.POJO.Response.GetAllBooksResponse;
import java.util.UUID;

public interface BookService {
    GetAllBooksResponse getAllBooks();
    BooksResponse getBooks(UUID bookId);
    BooksResponse saveBooks(BooksRequest request);
    BooksResponse updateBooks(UUID bookId, BooksRequest request);
    BooksResponse deleteBooks(UUID bookId);
}
