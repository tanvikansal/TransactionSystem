package com.visa.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visa.transaction.entity.Transaction;
import com.visa.transaction.exception.AccountNotFoundException;
import com.visa.transaction.exception.OperationTypeNotFoundException;
import com.visa.transaction.mapper.TransactionMapper;
import com.visa.transaction.request.TransactionRequest;
import com.visa.transaction.response.TransactionResponse;
import com.visa.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@Import(com.visa.transaction.exception.GlobalExceptionHandler.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService service;

    @MockBean
    private TransactionMapper mapper;

    // ✅ 1. SUCCESS CASE
    @Test
    void shouldCreateTransactionSuccessfully() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(UUID.randomUUID());
        request.setOperationTypeId(1);
        request.setAmount(BigDecimal.valueOf(100));

        Transaction entity = new Transaction();
        entity.setAccountId(request.getAccountId());
        entity.setAmount(BigDecimal.valueOf(-100));

        Transaction saved = new Transaction();
        saved.setAccountId(request.getAccountId());
        saved.setAmount(BigDecimal.valueOf(-100));

        TransactionResponse response = new TransactionResponse();
        response.setAccountId(request.getAccountId());
        response.setAmount(BigDecimal.valueOf(-100));

        when(mapper.toEntity(any())).thenReturn(entity);
        when(service.create(any())).thenReturn(saved);
        when(mapper.toResponse(any())).thenReturn(response);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.amount").value(-100));
    }

    // ❌ 2. ACCOUNT NOT FOUND → 404
    @Test
    void shouldReturn404WhenAccountNotFound() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(UUID.randomUUID());
        request.setOperationTypeId(1);
        request.setAmount(BigDecimal.valueOf(100));

        when(mapper.toEntity(any())).thenReturn(new Transaction());
        when(service.create(any()))
                .thenThrow(new AccountNotFoundException("Account not found"));

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("ACCOUNT_NOT_FOUND"));
    }

    // ❌ 3. OPERATION TYPE NOT FOUND → 404
    @Test
    void shouldReturn404WhenOperationTypeInvalid() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(UUID.randomUUID());
        request.setOperationTypeId(99);
        request.setAmount(BigDecimal.valueOf(100));

        when(mapper.toEntity(any())).thenReturn(new Transaction());
        when(service.create(any()))
                .thenThrow(new OperationTypeNotFoundException("Operation not found"));

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("OPERATION_TYPE_NOT_FOUND"));
    }

    // 💥 4. GENERIC ERROR → 500
    @Test
    void shouldReturn500WhenUnexpectedErrorOccurs() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(UUID.randomUUID());
        request.setOperationTypeId(1);
        request.setAmount(BigDecimal.valueOf(100));

        when(mapper.toEntity(any())).thenReturn(new Transaction());
        when(service.create(any()))
                .thenThrow(new RuntimeException("DB failure"));

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("INTERNAL_SERVER_ERROR"));
    }
}