package com.visa.transaction.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {

    @Id
    @UuidGenerator
    @Column(name = "transaction_id", nullable = false, updatable = false)
    private UUID transactionId;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "operation_type_id", nullable = false)
    private Integer operationTypeId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "event_date", nullable = false, updatable = false)
    private Instant eventDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}