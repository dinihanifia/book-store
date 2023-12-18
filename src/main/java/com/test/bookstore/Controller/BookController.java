package com.test.bookstore.Controller;
import com.test.bookstore.Config.URLConstant;
import com.test.bookstore.POJO.Request.BooksRequest;
import com.test.bookstore.POJO.Response.BooksResponse;
import com.test.bookstore.POJO.Response.GetAllBooksResponse;
import com.test.bookstore.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(URLConstant.BOOK_URL)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(URLConstant.GET_ALL_URL)
    public GetAllBooksResponse getAllBooks(){
        return bookService.getAllBooks();
    }
    @GetMapping(URLConstant.GET_ONE_URL)
    public BooksResponse getBooks(@RequestParam("bookId")UUID bookId){
        return bookService.getBooks(bookId);
    }
    @PostMapping(URLConstant.SAVE_URL)
    public BooksResponse saveBooks(@RequestBody BooksRequest request){
        return bookService.saveBooks(request);
    }
    @PutMapping(URLConstant.UPDATE_URL)
    public BooksResponse updateBooks(@RequestParam("bookId")UUID bookId, @RequestBody BooksRequest request){
        return bookService.updateBooks(bookId, request);
    }
    @DeleteMapping(URLConstant.DELETE_URL)
    public BooksResponse deleteBooks(@RequestParam("bookId")UUID bookId){
        return bookService.deleteBooks(bookId);
    }
}
