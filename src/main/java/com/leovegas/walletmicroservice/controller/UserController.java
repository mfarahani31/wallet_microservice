package com.leovegas.walletmicroservice.controller;

import com.leovegas.walletmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/currentBalance/{userId}")
    public ResponseEntity<Long> getCurrentBalance(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getCurrentBalance(userId));
    }
}
