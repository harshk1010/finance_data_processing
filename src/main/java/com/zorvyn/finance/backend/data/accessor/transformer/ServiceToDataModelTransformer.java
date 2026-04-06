package com.zorvyn.finance.backend.data.accessor.transformer;

import com.zorvyn.finance.backend.service.model.common.FinancialRecord;
import com.zorvyn.finance.backend.service.model.common.User;

public final class ServiceToDataModelTransformer {

    public com.zorvyn.finance.backend.data.model.User toUserEntity(User user) {
        return com.zorvyn.finance.backend.data.model.User.builder()
                .id(user.getId()).email(user.getEmail()).password(user.getPassword())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .roleType(user.getRoleType()).status(user.getStatus())
                .updatedByUserId(user.getId()).build();
    }

    public User toServiceUser(com.zorvyn.finance.backend.data.model.User entity) {
        return User.builder()
                .id(entity.getId()).email(entity.getEmail()).password(entity.getPassword())
                .firstName(entity.getFirstName()).lastName(entity.getLastName())
                .roleType(entity.getRoleType()).status(entity.getStatus()).build();
    }

    public com.zorvyn.finance.backend.data.model.FinancialRecord toFinancialRecordEntity(FinancialRecord r) {
        return com.zorvyn.finance.backend.data.model.FinancialRecord.builder()
                .id(r.getId()).amount(r.getAmount()).type(r.getType())
                .category(r.getCategory()).description(r.getDescription())
                .transactionTime(r.getTransactionTime()).deleted(false)
                .build();
    }

    public FinancialRecord toServiceFinancialRecord(com.zorvyn.finance.backend.data.model.FinancialRecord e) {
        return FinancialRecord.builder()
                .id(e.getId()).amount(e.getAmount()).type(e.getType())
                .category(e.getCategory()).description(e.getDescription())
                .transactionTime(e.getTransactionTime()).build();
    }
}