package com.visa.transaction.mapper;

import com.visa.transaction.entity.Transaction;
import com.visa.transaction.request.TransactionRequest;
import com.visa.transaction.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "eventDate", ignore = true) // set in service (UTC)
    Transaction toEntity(TransactionRequest request);

    // Optional: ensure consistent string format for API layer
    @Mapping(target = "eventDate", expression = "java(transaction.getEventDate().toString())")
    TransactionResponse toResponse(Transaction transaction);
}