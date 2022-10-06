package com.leovegas.walletmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@AllArgsConstructor
public class TransactionRequestDTO {
    @NotNull
    @Positive
    private Long amount;

    @NotNull
    private Long transaction_id;

}
