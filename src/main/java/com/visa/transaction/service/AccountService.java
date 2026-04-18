package com.visa.transaction.service;

import com.visa.transaction.entity.Account;
import com.visa.transaction.exception.NotFoundException;
import com.visa.transaction.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }
}
