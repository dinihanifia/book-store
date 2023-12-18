package com.test.bookstore.Service.Impl;
import com.test.bookstore.Config.HelperException;
import com.test.bookstore.Config.MessageConstant;
import com.test.bookstore.POJO.Entity.Books;
import com.test.bookstore.POJO.Entity.Transactions;
import com.test.bookstore.POJO.Request.TransactionRequest;
import com.test.bookstore.POJO.Response.GetAllTransactionResponse;
import com.test.bookstore.POJO.Response.TransactionResponse;
import com.test.bookstore.Repository.BookRepository;
import com.test.bookstore.Repository.TransactionRepository;
import com.test.bookstore.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "myCache")
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;

    @Override
    public GetAllTransactionResponse getAllTransaction() {
        GetAllTransactionResponse response = new GetAllTransactionResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            var checkTransaction = transactionRepository.findAll();
            List<Transactions> transactionsList = checkTransaction.stream().collect(Collectors.toList());
            return GetAllTransactionResponse.builder()
                    .responseCode(HttpStatus.OK.value())
                    .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                    .transactions(transactionsList)
                    .build();
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    @Cacheable(key = "#transactionId")
    public TransactionResponse getOneTransaction(UUID transactionId) {
        TransactionResponse response = new TransactionResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            Optional<Transactions> checkTransactions = transactionRepository.findById(transactionId);
            checkTransactions.ifPresentOrElse(
                    transaction -> {
                        response.setResponseCode(HttpStatus.OK.value());
                        response.setResponseMessage(MessageConstant.SUCCESS_MESSAGE);
                        response.setTransactions(transaction);
                    },
                    () -> {
                        response.setResponseCode(HttpStatus.NOT_FOUND.value());
                        response.setResponseMessage(MessageConstant.NOT_FOUND_MESSAGE);
                    });
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    @Transactional
    public TransactionResponse saveTransaction(TransactionRequest request) {
        TransactionResponse response = new TransactionResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            var checkBookId = bookRepository.findById(request.getBooks().getBookId());
            checkBookId.ifPresentOrElse(
                    bookId -> {
                        response.setResponseCode(HttpStatus.OK.value());
                        response.setResponseMessage(MessageConstant.SUCCESS_MESSAGE);
                    },
                    () -> {
                        response.setResponseCode(HttpStatus.NOT_FOUND.value());
                        response.setResponseMessage(MessageConstant.NOT_FOUND_MESSAGE);
                    });
            // update stock
            var book = checkBookId.get();
            var currentStock = book.getBookStock();
            var requestQuantity = request.getQuantity();
            var bookPrice = request.getBooks().getBookPrice();
            if (currentStock >= requestQuantity) {
                book.setBookStock(currentStock - requestQuantity);
                bookRepository.save(book);
            } else {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage(MessageConstant.INSUFFICIENT_STOCK_MESSAGE);
            }
            Transactions transactions = Transactions.builder()
                    .books(book)
                    .quantity(requestQuantity)
                    .totalPrice(requestQuantity * bookPrice)
                    .createdBy(request.getCreatedBy())
                    .build();
            var save = transactionRepository.save(transactions);
            return TransactionResponse.builder()
                    .responseCode(HttpStatus.OK.value())
                    .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                    .transactions(save)
                    .build();
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    @Transactional
    public TransactionResponse updateTransaction(UUID transactionId, TransactionRequest request) {
        TransactionResponse response = new TransactionResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            // cek transaksi,buku
            Transactions checkTransaction = transactionRepository.findById(transactionId)
                    .orElseThrow(() -> new HelperException(MessageConstant.NOT_FOUND_MESSAGE));
            Books checkBooks = bookRepository.findById(request.getBooks().getBookId())
                    .orElseThrow(() -> new HelperException(MessageConstant.NOT_FOUND_MESSAGE));
            
            var currentStock = checkBooks.getBookStock();
            var requestQuantity = request.getQuantity();
            // update stock buku
            if (currentStock >= requestQuantity) {
                checkTransaction.getBooks().setBookStock(currentStock - requestQuantity);
                bookRepository.save(checkBooks);
            } else {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage(MessageConstant.INSUFFICIENT_STOCK_MESSAGE);
            }
            // update
            checkTransaction.setQuantity(requestQuantity);
            checkTransaction.setBooks(checkBooks);
            checkTransaction.setCreatedBy(request.getCreatedBy());
            // save update
            var save = transactionRepository.save(checkTransaction);
            return TransactionResponse.builder()
                        .responseCode(HttpStatus.OK.value())
                        .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                        .transactions(save)
                        .build();
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage(MessageConstant.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return response;
    }

    @Override
    public TransactionResponse deleteTransaction(UUID transactionId) {
        TransactionResponse response = new TransactionResponse();
        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        response.setResponseMessage(MessageConstant.BAD_REQUEST_MESSAGE);
        try {
            var checkTransaction = getOneTransaction(transactionId);
            if (checkTransaction.getResponseCode() == HttpStatus.OK.value()) {
                transactionRepository.deleteById(transactionId);
                return TransactionResponse.builder()
                        .responseCode(HttpStatus.OK.value())
                        .responseMessage(MessageConstant.SUCCESS_MESSAGE)
                        .build();
            }
            return TransactionResponse.builder()
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
