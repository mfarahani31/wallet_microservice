package com.leovegas.walletmicroservice.repository;

import com.leovegas.walletmicroservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select t from Transaction t where t.user.id = :userId")
    List<Transaction> getAllByUserId(@Param("userId") Long userId);
}
