package com.leovegas.walletmicroservice.controller;

import com.leovegas.walletmicroservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get current balance of a user by userId")
    @ApiResponse(responseCode = "200", description = "Found the current balance", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Long.class))})
    @GetMapping("/currentBalance/{userId}")
    public ResponseEntity<Long> getCurrentBalance(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getCurrentBalance(userId));
    }
}
