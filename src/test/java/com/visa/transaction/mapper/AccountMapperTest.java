package com.visa.transaction.mapper;

import com.visa.transaction.entity.Account;
import com.visa.transaction.request.AccountRequest;
import com.visa.transaction.response.AccountResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

    private final AccountMapper accountMapper =
            Mappers.getMapper(AccountMapper.class);

    @Test
    void shouldMapRequestToEntity() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber("123456");

        Account account = accountMapper.toEntity(request);

        assertNotNull(account);
        assertEquals(123456L, account.getDocumentNumber());
        assertNotNull(account.getAccountId());
    }

    @Test
    void shouldMapEntityToResponse() {
        Account account = new Account();
        UUID id = UUID.randomUUID();
        account.setAccountId(id);
        account.setDocumentNumber(987654L);

        AccountResponse response = accountMapper.toResponse(account);

        assertNotNull(response);
        assertEquals(id.toString(), response.getAccountId());
        assertEquals(987654L, response.getDocumentNumber());
    }

    @Test
    void shouldThrowExceptionForInvalidNumber() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber("abc");

        assertThrows(NumberFormatException.class,
                () -> accountMapper.toEntity(request));
    }

    @Test
    void shouldThrowExceptionForNullNumber() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber(null);

        assertThrows(RuntimeException.class,
                () -> accountMapper.toEntity(request));
    }
}