package com.visa.transaction.service;

import com.visa.transaction.entity.Transaction;
import com.visa.transaction.exception.AccountNotFoundException;
import com.visa.transaction.operations.OperationTypeStrategy;
import com.visa.transaction.operations.OperationTypeStrategyFactory;
import com.visa.transaction.repository.AccountRepository;
import com.visa.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final OperationTypeStrategyFactory factory;
    private final AccountRepository accountRepository;
    private final Clock clock;

    public TransactionService(TransactionRepository repository,
                              OperationTypeStrategyFactory factory,
                              AccountRepository accountRepository) {
        this.repository = repository;
        this.factory = factory;
        this.accountRepository = accountRepository;
        this.clock = Clock.systemUTC(); // ✅ UTC clock
    }

    public Transaction create(Transaction transaction) {

        UUID accountId = transaction.getAccountId();

        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException(
                    "Account not found: " + accountId
            );
        }

        OperationTypeStrategy strategy =
                factory.getStrategy(transaction.getOperationTypeId());

        transaction.setAmount(
                strategy.apply(transaction.getAmount())
        );

        // ✅ UTC timestamp (IMPORTANT CHANGE)
        transaction.setEventDate(Instant.now(clock));

        return repository.save(transaction);
    }
}