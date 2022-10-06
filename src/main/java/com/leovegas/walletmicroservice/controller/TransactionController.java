package com.leovegas.walletmicroservice.controller;

import com.leovegas.walletmicroservice.dto.TransactionRequestDTO;
import com.leovegas.walletmicroservice.dto.TransactionResponseDTO;
import com.leovegas.walletmicroservice.mapper.TransactionMapper;
import com.leovegas.walletmicroservice.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private static final Logger logger =
            LoggerFactory.getLogger(TransactionController.class);
    private static final String NEW_TRANSACTION_LOG = "New transaction was created :{}";

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/credit/{userId}")
    public ResponseEntity<TransactionResponseDTO> creditByUserId(@PathVariable Long userId, @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO responseDTO = this.transactionService.creditByUserId(userId, TransactionMapper.INSTANCE.toTransaction(transactionRequestDTO));
        logger.info(NEW_TRANSACTION_LOG, responseDTO.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/debit/{userId}")
    public ResponseEntity<TransactionResponseDTO> debitByUserId(@PathVariable Long userId, @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO responseDTO = this.transactionService.debitByUserId(userId, TransactionMapper.INSTANCE.toTransaction(transactionRequestDTO));
        logger.info(NEW_TRANSACTION_LOG, responseDTO.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<List<TransactionResponseDTO>> getAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.transactionService.getAllByUserId(userId));
    }

}
