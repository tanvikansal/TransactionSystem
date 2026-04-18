package com.visa.transaction.mapper;

import com.visa.transaction.entity.Account;
import com.visa.transaction.request.AccountRequest;
import com.visa.transaction.response.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        imports = java.util.UUID.class)
public interface AccountMapper {

    @Mapping(target = "documentNumber", expression = "java(Long.parseLong(request.getDocumentNumber()))")
    @Mapping(target = "accountId", expression = "java(UUID.randomUUID())")
    Account toEntity(AccountRequest request);

    default AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getAccountId().toString(),
                account.getDocumentNumber()
        );
    }
}
