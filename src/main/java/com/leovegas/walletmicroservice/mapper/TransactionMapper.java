package com.leovegas.walletmicroservice.mapper;

import com.leovegas.walletmicroservice.dto.TransactionRequestDTO;
import com.leovegas.walletmicroservice.dto.TransactionResponseDTO;
import com.leovegas.walletmicroservice.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    Transaction toTransaction(TransactionRequestDTO transactionRequestDTO);

    TransactionResponseDTO toTransactionResponseDTO(Transaction transaction);

    List<TransactionResponseDTO> toListOfTransactionResponseDTOs(List<Transaction> transactions);

}
