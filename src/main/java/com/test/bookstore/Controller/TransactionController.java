package com.test.bookstore.Controller;
import com.test.bookstore.Config.URLConstant;
import com.test.bookstore.POJO.Request.TransactionRequest;
import com.test.bookstore.POJO.Response.GetAllTransactionResponse;
import com.test.bookstore.POJO.Response.TransactionResponse;
import com.test.bookstore.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping(URLConstant.TRANSACTION_URL)
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(URLConstant.GET_ALL_URL)
    public GetAllTransactionResponse getAllTransaction(){
        return transactionService.getAllTransaction();
    }
    @GetMapping(URLConstant.GET_ONE_URL)
    public TransactionResponse getOneTransaction(@RequestParam("transactionId")UUID transactionId){
        return transactionService.getOneTransaction(transactionId);
    }
    @PostMapping(URLConstant.SAVE_URL)
    public TransactionResponse saveTransaction(@RequestBody TransactionRequest request){
        return transactionService.saveTransaction(request);
    }
    @PutMapping(URLConstant.UPDATE_URL)
    public TransactionResponse updateTransaction(@RequestParam("transactionId")UUID transactionId, @RequestBody TransactionRequest request){
        return transactionService.updateTransaction(transactionId, request);
    }
    @DeleteMapping(URLConstant.DELETE_URL)
    public TransactionResponse deleteTransaction(@RequestParam("transactionId")UUID transactionId){
        return transactionService.deleteTransaction(transactionId);
    }
}
