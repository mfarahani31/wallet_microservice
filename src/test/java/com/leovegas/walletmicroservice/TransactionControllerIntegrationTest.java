package com.leovegas.walletmicroservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.leovegas.walletmicroservice.dto.TransactionRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void creditByUserId() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(MotherObject.getAnyTransactionRequestDTO());


        mockMvc.perform(post("/api/v1/transactions/credit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.amount").value("100"))
                .andExpect(
                        status().isCreated());
    }


    @Test
    public void creditByUserIdWhenIdNotExistThrowsException() throws Exception {
        mockMvc.perform(post("/api/v1/transactions/credit/5"))
                .andDo(print())
                .andExpect(
                        status().is4xxClientError());
    }

    @Test
    public void debitByUserId() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        TransactionRequestDTO testObject = MotherObject.getAnyTransactionRequestDTO();
        testObject.setTransaction_id(9L);
        String requestJson = ow.writeValueAsString(testObject);


        mockMvc.perform(post("/api/v1/transactions/debit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.amount").value("100"))
                .andExpect(
                        status().isCreated());
    }

    @Test
    public void testDebitByUserId_whenIdNotExist_thenThrowsException() throws Exception {
        mockMvc.perform(post("/api/v1/transactions/debit/5"))
                .andDo(print())
                .andExpect(
                        status().is4xxClientError());
    }

    @Test
    public void testDebitByUserId_whenBadRequest_thenThrowsException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        TransactionRequestDTO testObject = MotherObject.getAnyTransactionRequestDTO();
        testObject.setAmount(4500L);
        testObject.setTransaction_id(10L);
        String requestJson = ow.writeValueAsString(testObject);

        mockMvc.perform(post("/api/v1/transactions/debit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(
                        status().is4xxClientError());
    }

    @Test
    public void testGetAllTransactionByUserId() throws Exception {
        mockMvc.perform(get("/api/v1/transactions/byUserId/1"))
                .andDo(print())
                .andExpect(jsonPath("$.[0].amount").value("100"))
                .andExpect(jsonPath("$.[0].transaction_id").value("1"))
                .andExpect(
                        status().isOk());
    }

    @Test
    public void testGetAllTransactionByUserId_whenIdNotExist_thenThrowsException() throws Exception {
        mockMvc.perform(get("/api/v1/transactions/byUserId/50"))
                .andDo(print())
                .andExpect(jsonPath("$").isEmpty())
                .andExpect(
                        status().isOk());
    }
}
