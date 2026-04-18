package com.visa.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visa.transaction.entity.Account;
import com.visa.transaction.exception.NotFoundException;
import com.visa.transaction.mapper.AccountMapper;
import com.visa.transaction.request.AccountRequest;
import com.visa.transaction.response.AccountResponse;
import com.visa.transaction.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.visa.transaction.exception.GlobalExceptionHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountMapper mapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AccountController(accountService, mapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ✅ 1. Happy path test
    @Test
    void shouldCreateAccountSuccessfully() throws Exception {

        AccountRequest request = new AccountRequest();
        request.setDocumentNumber("12345678900");

        Account account = new Account();
        account.setDocumentNumber(12345678900L);

        Account dummyAccount = new Account();
        dummyAccount.setAccountId(UUID.randomUUID());
        dummyAccount.setDocumentNumber(12345678900L);

        when(mapper.toEntity(any(AccountRequest.class)))
                .thenReturn(account);

        when(accountService.createAccount(any(Account.class)))
                .thenReturn(dummyAccount);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.documentNumber").value(12345678900L));
    }

    // ❌ 2. Validation failure test
    @Test
    void shouldReturn400WhenDocumentNumberMissing() throws Exception {

        AccountRequest request = new AccountRequest(); // no documentNumber

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.details[0].field").value("documentNumber"));
    }

    // 💥 3. Internal server error test
    @Test
    void shouldReturn500WhenServiceThrowsException() throws Exception {

        when(mapper.toEntity(any(AccountRequest.class)))
                .thenReturn(new Account());

        when(accountService.createAccount(any(Account.class)))
                .thenThrow(new RuntimeException("DB failure"));

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"document_number\":\"12345678900\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("Something went wrong"));
    }

    @Test
    void shouldReturn404WhenAccountNotFound() throws Exception {

        UUID id = UUID.randomUUID();

        when(accountService.getAccount(id))
                .thenThrow(new NotFoundException("Account not found"));

        mockMvc.perform(get("/accounts/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Account not found"));
    }

    @Test
    void shouldReturn400WhenAccountIdIsInvalidUUID() throws Exception {

        mockMvc.perform(get("/accounts/invalid-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"));
    }

    @Test
    void shouldReturn400WhenDocumentNumberIsInvalid() throws Exception {

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"document_number\":\"abc123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.details[0].field").value("documentNumber"));
    }

    @Test
    void shouldReturnAccountSuccessfully() throws Exception {

        UUID id = UUID.randomUUID();

        Account account = new Account();
        account.setAccountId(id);
        account.setDocumentNumber(12345678900L);

        AccountResponse response = new AccountResponse(id.toString(), 12345678900L);

        when(accountService.getAccount(id))
                .thenReturn(account);

        when(mapper.toResponse(account))
                .thenReturn(response);

        mockMvc.perform(get("/accounts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(id.toString()))
                .andExpect(jsonPath("$.documentNumber").value(12345678900L));
    }
}