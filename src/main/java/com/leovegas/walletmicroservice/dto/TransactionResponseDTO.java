package com.leovegas.walletmicroservice.dto;

import com.leovegas.walletmicroservice.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
public class TransactionResponseDTO {
    private Long amount;

    private Long transaction_id;

    private TransactionType type;

    private Date createdAt;

    private Date updatedAt;
}
