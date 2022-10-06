package com.leovegas.walletmicroservice.service.impl;

import com.leovegas.walletmicroservice.MotherObject;
import com.leovegas.walletmicroservice.exception.BadRequestException;
import com.leovegas.walletmicroservice.exception.ElementNotFoundException;
import com.leovegas.walletmicroservice.model.User;
import com.leovegas.walletmicroservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultUserServiceTest {
    @InjectMocks
    DefaultUserService userService;

    @Mock
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = MotherObject.getAnyUser();
    }

    @Test
    void testContext() {
        assertNotNull(userService);
        assertNotNull(userRepository);
    }

    @Test
    void getCurrentBalance() {
        when(userRepository.getBalance(1L)).thenReturn(Optional.of(this.user.getBalance()));

        Long balance = userService.getCurrentBalance(1L);

        assertEquals(100L, balance);
    }

    @Test
    void getCurrentBalance_throws_exception() {
        when(userRepository.getBalance(1L)).thenThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class, () -> userService.getCurrentBalance(1L));
    }

    @Test
    void creditByUserId() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(this.user));

        userService.creditByUserId(user.getId(), 100L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(this.user);
    }

    @Test
    void creditByUserId_throws_exception() {
        when(userRepository.findById(10L)).thenThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class, () -> userService.creditByUserId(1L, 100L));
    }

    @Test
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(this.user));

        Optional<User> user = userService.findById(1L);

        assertEquals("John", user.get().getName());
        assertEquals(100L, user.get().getBalance());
    }

    @Test
    void findById_throws_exception() {
        when(userRepository.findById(1L)).thenThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void debitByUserId() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(this.user));
        when(userRepository.save(this.user)).thenReturn(this.user);

        userService.debitByUserId(user.getId(), 100L);

        verify(userRepository, times(1)).save(this.user);
    }

    @Test
    void debitByUserId_throws_exception() {
        when(userRepository.findById(1L)).thenThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class, () -> userService.debitByUserId(1L, 100L));
    }

    @Test
    void debitByUserId_when_amountIsMoreThanBalance_then_throws_exception() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(this.user));
        when(userRepository.save(this.user)).thenReturn(this.user);


        userService.debitByUserId(user.getId(), 100L);


        assertThrows(BadRequestException.class, () -> userService.debitByUserId(1L, 1000L));
    }
}