package com.visa.transaction.service;

import com.visa.transaction.entity.Account;
import com.visa.transaction.exception.NotFoundException;
import com.visa.transaction.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldReturnAccount() {
        Account account = new Account();
        UUID id = UUID.randomUUID();

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        assertEquals(account, accountService.getAccount(id));
    }

    @Test
    void shouldThrowWhenNotFound() {
        UUID id = UUID.randomUUID();

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> accountService.getAccount(id));
    }
}
