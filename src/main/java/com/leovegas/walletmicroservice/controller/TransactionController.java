package com.leovegas.walletmicroservice.controller;

import com.leovegas.walletmicroservice.dto.TransactionRequestDTO;
import com.leovegas.walletmicroservice.dto.TransactionResponseDTO;
import com.leovegas.walletmicroservice.mapper.TransactionMapper;
import com.leovegas.walletmicroservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @Operation(summary = "Crate a new Credit transaction")
    @ApiResponse(responseCode = "201", description = "Transaction credit is created", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionResponseDTO.class))})
    @PostMapping(value = "/credit/{userId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDTO> creditByUserId(@PathVariable Long userId, @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO responseDTO = this.transactionService.creditByUserId(userId, TransactionMapper.INSTANCE.toTransaction(transactionRequestDTO));
        logger.info(NEW_TRANSACTION_LOG, responseDTO.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "Crate a new Debit transaction")
    @ApiResponse(responseCode = "201", description = "Transaction debit is created", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionResponseDTO.class))})
    @PostMapping(value = "/debit/{userId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDTO> debitByUserId(@PathVariable Long userId, @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO responseDTO = this.transactionService.debitByUserId(userId, TransactionMapper.INSTANCE.toTransaction(transactionRequestDTO));
        logger.info(NEW_TRANSACTION_LOG, responseDTO.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "Get list of transactions by userId for a specific user")
    @ApiResponse(responseCode = "200", description = "Found the transactions", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionResponseDTO.class))})
    @GetMapping(value = "/byUserId/{userId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionResponseDTO>> getAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.transactionService.getAllByUserId(userId));
    }

}
