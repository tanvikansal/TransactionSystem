package com.visa.transaction.service;

import com.visa.transaction.entity.Transaction;
import com.visa.transaction.exception.AccountNotFoundException;
import com.visa.transaction.operations.OperationTypeStrategy;
import com.visa.transaction.operations.OperationTypeStrategyFactory;
import com.visa.transaction.repository.AccountRepository;
import com.visa.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService service;

    @Mock
    private TransactionRepository repository;

    @Mock
    private OperationTypeStrategyFactory factory;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationTypeStrategy strategy;

    // ✅ 1. SUCCESS FLOW
    @Test
    void shouldCreateTransactionSuccessfully() {

        UUID accountId = UUID.randomUUID();

        Transaction tx = new Transaction();
        tx.setAccountId(accountId);
        tx.setOperationTypeId(1);
        tx.setAmount(BigDecimal.valueOf(100));

        when(accountRepository.existsById(accountId)).thenReturn(true);
        when(factory.getStrategy(1)).thenReturn(strategy);
        when(strategy.apply(BigDecimal.valueOf(100)))
                .thenReturn(BigDecimal.valueOf(-100));

        when(repository.save(any(Transaction.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Transaction result = service.create(tx);

        assertEquals(BigDecimal.valueOf(-100), result.getAmount());
        assertNotNull(result.getEventDate());

        verify(accountRepository).existsById(accountId);
        verify(factory).getStrategy(1);
        verify(repository).save(any(Transaction.class));
    }

    // ❌ 2. ACCOUNT NOT FOUND
    @Test
    void shouldThrowExceptionWhenAccountNotFound() {

        UUID accountId = UUID.randomUUID();

        Transaction tx = new Transaction();
        tx.setAccountId(accountId);

        when(accountRepository.existsById(accountId)).thenReturn(false);

        assertThrows(AccountNotFoundException.class,
                () -> service.create(tx));

        verify(factory, never()).getStrategy(any());
        verify(repository, never()).save(any());
    }

    // ❌ 3. STRATEGY NOT FOUND (via factory)
    @Test
    void shouldThrowExceptionWhenOperationTypeInvalid() {

        UUID accountId = UUID.randomUUID();

        Transaction tx = new Transaction();
        tx.setAccountId(accountId);
        tx.setOperationTypeId(99);
        tx.setAmount(BigDecimal.valueOf(100));

        when(accountRepository.existsById(accountId)).thenReturn(true);
        when(factory.getStrategy(99))
                .thenThrow(new RuntimeException("Operation type not found"));

        assertThrows(RuntimeException.class,
                () -> service.create(tx));

        verify(repository, never()).save(any());
    }

    // ✅ 4. VERIFY EVENT DATE IS ALWAYS SET
    @Test
    void shouldSetEventDateForTransaction() {

        UUID accountId = UUID.randomUUID();

        Transaction tx = new Transaction();
        tx.setAccountId(accountId);
        tx.setOperationTypeId(1);
        tx.setAmount(BigDecimal.valueOf(100));

        when(accountRepository.existsById(accountId)).thenReturn(true);
        when(factory.getStrategy(any())).thenReturn(strategy);
        when(strategy.apply(any())).thenReturn(BigDecimal.valueOf(-100));

        when(repository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        Transaction result = service.create(tx);

        assertNotNull(result.getEventDate());
    }
}