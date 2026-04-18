package com.visa.transaction.controller;

import com.visa.transaction.entity.Account;
import com.visa.transaction.mapper.AccountMapper;
import com.visa.transaction.request.AccountRequest;
import com.visa.transaction.response.AccountResponse;
import com.visa.transaction.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper mapper;

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest request) {
        Account account = mapper.toEntity(request);
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable String accountId) {

        UUID id = UUID.fromString(accountId);

        Account account = accountService.getAccount(id);

        AccountResponse response = mapper.toResponse(account);

        return ResponseEntity.ok(response);
    }
}
