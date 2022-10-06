package com.leovegas.walletmicroservice.service;

import com.leovegas.walletmicroservice.dto.TransactionResponseDTO;
import com.leovegas.walletmicroservice.model.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionResponseDTO creditByUserId(Long userId, Transaction transactionRequest);

    TransactionResponseDTO debitByUserId(Long userId, Transaction transactionRequest);

    List<TransactionResponseDTO> getAllByUserId(Long userId);
}
