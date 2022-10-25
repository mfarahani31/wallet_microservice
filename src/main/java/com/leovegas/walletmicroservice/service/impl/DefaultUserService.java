package com.leovegas.walletmicroservice.service.impl;

import com.leovegas.walletmicroservice.constants.ErrorMessage;
import com.leovegas.walletmicroservice.exception.BadRequestException;
import com.leovegas.walletmicroservice.exception.ElementNotFoundException;
import com.leovegas.walletmicroservice.model.User;
import com.leovegas.walletmicroservice.repository.UserRepository;
import com.leovegas.walletmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long getCurrentBalance(Long userId) {
        return this.userRepository.getBalance(userId).orElseThrow(
                () -> new ElementNotFoundException(ErrorMessage.ERROR_FOR_TRANSACTION_GET_BALANCE + userId));
    }

    @Override
    public void creditByUserId(Long userId, Long amount) {
        var user = this.findById(userId).
                orElseThrow(() -> new ElementNotFoundException(ErrorMessage.ERROR_FOR_TRANSACTION_GET_BALANCE + userId));
        user.setBalance(user.getBalance() + amount);
        this.userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public void debitByUserId(Long userId, Long amount) {
        var user = this.findById(userId).
                orElseThrow(() -> new ElementNotFoundException(ErrorMessage.ERROR_NOT_USER_EXIST + userId));
        if (user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
        } else
            throw new BadRequestException(ErrorMessage.ERROR_DEBIT_MORE_THAN_BALANCE);
        this.userRepository.save(user);
    }
}
