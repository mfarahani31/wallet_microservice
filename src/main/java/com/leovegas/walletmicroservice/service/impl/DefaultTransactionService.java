package com.leovegas.walletmicroservice.service.impl;

import com.leovegas.walletmicroservice.constants.ErrorMessage;
import com.leovegas.walletmicroservice.dto.TransactionResponseDTO;
import com.leovegas.walletmicroservice.exception.BadRequestException;
import com.leovegas.walletmicroservice.mapper.TransactionMapper;
import com.leovegas.walletmicroservice.model.Transaction;
import com.leovegas.walletmicroservice.model.TransactionType;
import com.leovegas.walletmicroservice.repository.TransactionRepository;
import com.leovegas.walletmicroservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DefaultTransactionService implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final DefaultUserService userService;

    @Autowired
    public DefaultTransactionService(TransactionRepository transactionRepository, DefaultUserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public TransactionResponseDTO creditByUserId(Long userId, Transaction transactionRequest) {
        transactionRequest.setType(TransactionType.CREDIT);
        this.userService.creditByUserId(userId, transactionRequest.getAmount());

        return this.userService.findById(userId).map(user -> {
            transactionRequest.setUser(user);
            return TransactionMapper.INSTANCE.toTransactionResponseDTO(transactionRepository.save(transactionRequest));
        }).orElseThrow(() -> new BadRequestException(ErrorMessage.ERROR_FOR_TRANSACTION));
    }

    @Transactional
    @Override
    public TransactionResponseDTO debitByUserId(Long userId, Transaction transactionRequest) {
        transactionRequest.setType(TransactionType.DEBIT);
        this.userService.debitByUserId(userId, transactionRequest.getAmount());

        return this.userService.findById(userId).map(user -> {
            transactionRequest.setUser(user);
            return TransactionMapper.INSTANCE.toTransactionResponseDTO(transactionRepository.save(transactionRequest));
        }).orElseThrow(() -> new BadRequestException(ErrorMessage.ERROR_FOR_TRANSACTION));
    }

    @Override
    public List<TransactionResponseDTO> getAllByUserId(Long userId) {
        return TransactionMapper.INSTANCE.toListOfTransactionResponseDTOs(this.transactionRepository.getAllByUserId(userId));
    }
}
