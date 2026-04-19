package com.visa.transaction.mapper;

import com.visa.transaction.entity.Transaction;
import com.visa.transaction.request.TransactionRequest;
import com.visa.transaction.response.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {

    private final TransactionMapper mapper =
            Mappers.getMapper(TransactionMapper.class);

    // ✅ 1. REQUEST → ENTITY
    @Test
    void shouldMapRequestToEntity() {

        UUID accountId = UUID.randomUUID();

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(accountId);
        request.setOperationTypeId(1);
        request.setAmount(BigDecimal.valueOf(100));

        Transaction entity = mapper.toEntity(request);

        assertEquals(accountId, entity.getAccountId());
        assertEquals(1, entity.getOperationTypeId());
        assertEquals(BigDecimal.valueOf(100), entity.getAmount());

        // ignored fields must be null
        assertNull(entity.getTransactionId());
        assertNull(entity.getEventDate());
    }

    // ✅ 2. ENTITY → RESPONSE (UPDATED FOR Instant → String)
    @Test
    void shouldMapEntityToResponse() {

        UUID accountId = UUID.randomUUID();
        Instant now = Instant.now();

        Transaction entity = new Transaction();
        entity.setTransactionId(UUID.randomUUID());
        entity.setAccountId(accountId);
        entity.setOperationTypeId(2);
        entity.setAmount(BigDecimal.valueOf(-100));
        entity.setEventDate(now);

        TransactionResponse response = mapper.toResponse(entity);

        assertEquals(entity.getTransactionId(), response.getTransactionId());
        assertEquals(accountId, response.getAccountId());
        assertEquals(BigDecimal.valueOf(-100), response.getAmount());

        // ✅ NEW ASSERTION (important fix)
        assertEquals(now.toString(), response.getEventDate());
    }
}