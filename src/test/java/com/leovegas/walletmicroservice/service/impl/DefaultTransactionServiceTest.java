package com.leovegas.walletmicroservice.service.impl;

import com.leovegas.walletmicroservice.MotherObject;
import com.leovegas.walletmicroservice.dto.TransactionResponseDTO;
import com.leovegas.walletmicroservice.exception.BadRequestException;
import com.leovegas.walletmicroservice.exception.ElementNotFoundException;
import com.leovegas.walletmicroservice.mapper.TransactionMapper;
import com.leovegas.walletmicroservice.model.Transaction;
import com.leovegas.walletmicroservice.model.User;
import com.leovegas.walletmicroservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class DefaultTransactionServiceTest {
    @InjectMocks
    DefaultTransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    DefaultUserService userService;

    Transaction transaction;
    List<Transaction> transactions;
    User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transaction = MotherObject.getAnyTransaction();
        transactions = MotherObject.getAnyListOfTransactions();
        user = MotherObject.getAnyUser();
    }

    @Test
    void testContext() {
        assertNotNull(userService);
        assertNotNull(transactionService);
        assertNotNull(transactionRepository);
    }

    @Test
    void testCreditByUserId() {
        doNothing().when(userService).creditByUserId(1L, 100L);
        when(userService.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        TransactionResponseDTO actualResponseTransaction = transactionService.creditByUserId(1L, transaction);

        assertEquals(TransactionMapper.INSTANCE.toTransactionResponseDTO(transaction), actualResponseTransaction);
    }

    @Test
    void testCreditByUserId_WhenIdNotExist_ThenThrowsException() {
        doNothing().when(userService).creditByUserId(1L, 100L);
        when(userService.findById(1L)).thenThrow(ElementNotFoundException.class);

        assertThrows(ElementNotFoundException.class, () -> transactionService.creditByUserId(1L, transaction));
    }

    @Test
    void testGetAllByUserId() {
        when(transactionRepository.getAllByUserId(user.getId())).thenReturn(transactions);

        List<TransactionResponseDTO> actualTransactions = transactionService.getAllByUserId(1L);

        assertEquals(transactions.size(), actualTransactions.size());
    }

    @Test
    void testDebitByUserId() {
        doNothing().when(userService).debitByUserId(1L, 10L);
        when(userService.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        TransactionResponseDTO actualResponseTransaction = transactionService.debitByUserId(1L, transaction);

        assertEquals(TransactionMapper.INSTANCE.toTransactionResponseDTO(transaction), actualResponseTransaction);
    }

    @Test
    void testDebitByUserId_WhenIdNotExist_ThenThrowsException() {
        doNothing().when(userService).debitByUserId(1L, 100L);
        when(userService.findById(1L)).thenThrow(ElementNotFoundException.class);

        assertThrows(ElementNotFoundException.class, () -> transactionService.debitByUserId(1L, transaction));
    }

    @Test
    void testDebitByUserId_WhenBadRequest_ThenThrowsException() {
        doNothing().when(userService).debitByUserId(1L, 100L);
        when(userService.findById(1L)).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> transactionService.debitByUserId(1L, transaction));
    }
}