package com.leovegas.walletmicroservice;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getCurrentBalanceByUserId() throws Exception {
        mockMvc.perform(get("/api/v1/users/currentBalance/1"))
                .andDo(print())
                .andExpect(jsonPath("$").value("100"))
                .andExpect(
                        status().isOk());
    }

    @Test
    public void getCurrentBalanceByUserIdWhenIdNotExistThrowsException() throws Exception {
        mockMvc.perform(get("/api/v1/users/currentBalance/5"))
                .andDo(print())
                .andExpect(
                        status().is4xxClientError());
    }
}
