package com.visa.transaction.controller;

import com.visa.transaction.entity.Transaction;
import com.visa.transaction.mapper.TransactionMapper;
import com.visa.transaction.request.TransactionRequest;
import com.visa.transaction.response.TransactionResponse;
import com.visa.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;
    private final TransactionMapper mapper;

    public TransactionController(TransactionService service,
                                 TransactionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody TransactionRequest request) {

        // 1. DTO → Entity
        Transaction tx = mapper.toEntity(request);

        // 2. Business logic
        Transaction saved = service.create(tx);

        // 3. Entity → DTO
        TransactionResponse response = mapper.toResponse(saved);

        return ResponseEntity.ok(response);
    }
}
