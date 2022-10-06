package com.leovegas.walletmicroservice;

import com.leovegas.walletmicroservice.dto.TransactionRequestDTO;
import com.leovegas.walletmicroservice.model.Transaction;
import com.leovegas.walletmicroservice.model.TransactionType;
import com.leovegas.walletmicroservice.model.User;

import java.util.ArrayList;
import java.util.List;

public class MotherObject {
    public static User getAnyUser() {
        return new User(1L, "John", 100L, null);
    }

    public static Transaction getAnyTransaction() {
        return new Transaction(1L, TransactionType.CREDIT, 100L, getAnyUser());
    }

    public static TransactionRequestDTO getAnyTransactionRequestDTO() {
        return new TransactionRequestDTO(100L, 5L);
    }

    public static List<Transaction> getAnyListOfTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(getAnyTransaction());
        transactions.add(getAnyTransaction());
        transactions.add(getAnyTransaction());
        return transactions;
    }
}
