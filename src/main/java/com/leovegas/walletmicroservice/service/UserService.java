package com.leovegas.walletmicroservice.service;

import com.leovegas.walletmicroservice.model.User;

import java.util.Optional;

public interface UserService {
    Long getCurrentBalance(Long userId);

    void creditByUserId(Long userId, Long amount);

    void debitByUserId(Long userId, Long amount);

    Optional<User> findById(Long userId);
}
