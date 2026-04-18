package com.visa.transaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter

public class Account {
    @Id
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "document_id", nullable = false)
    private Long documentNumber;
}
