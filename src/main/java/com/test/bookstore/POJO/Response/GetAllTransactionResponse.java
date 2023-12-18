package com.test.bookstore.POJO.Response;
import com.test.bookstore.POJO.Entity.Transactions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetAllTransactionResponse {
    private int responseCode;
    private String responseMessage;
    private List<Transactions> transactions;
}
