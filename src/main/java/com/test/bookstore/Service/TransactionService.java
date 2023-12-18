package com.test.bookstore.Service;
import com.test.bookstore.POJO.Request.TransactionRequest;
import com.test.bookstore.POJO.Response.GetAllTransactionResponse;
import com.test.bookstore.POJO.Response.TransactionResponse;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    GetAllTransactionResponse getAllTransaction();
    TransactionResponse getOneTransaction(UUID transactionId);
    TransactionResponse saveTransaction(TransactionRequest request);
    TransactionResponse updateTransaction(UUID transactionId, TransactionRequest request);
    TransactionResponse deleteTransaction(UUID transactionId);
}
