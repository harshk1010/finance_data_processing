package com.zorvyn.finance.backend.module;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.service.model.request.*;
import com.zorvyn.finance.backend.service.validator.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidatorModule {

    @NonNull
    private IFinancialRecordAccessor financialRecordAccessor;

    @NonNull
    private IUserDataAccessor userDataAccessor;

    @Bean
    public IValidator<CreateUserRequest> createUserValidator() {
        return new CreateUserValidator(userDataAccessor);
    }

    @Bean
    public IValidator<CreateFinancialRecordRequest> createFinancialRecordValidator() {
        return new CreateFinancialRecordValidator(financialRecordAccessor);
    }

    @Bean
    public IValidator<DeleteUserRequest> deleteUserValidator() {
        return new DeleteUserValidator(userDataAccessor);
    }

    @Bean
    public IValidator<DeleteFinancialRecordRequest> deleteFinancialRecordValidator() {
        return new DeleteFinancialRecordValidator(financialRecordAccessor);
    }

    @Bean
    public IValidator<LookupUsersRequest> lookupUsersValidator() {
        return new LookupUsersValidator(userDataAccessor);
    }

    @Bean
    public IValidator<LookupFinancialRecordsRequest> lookupFinancialRecordsValidator() {
        return new LookupFinancialRecordsValidator(financialRecordAccessor);
    }

    @Bean
    public IValidator<SummarizeFinancialRecordsRequest> summarizeFinancialRecordsValidator() {
        return new SummarizeFinancialRecordsValidator(financialRecordAccessor);
    }

    @Bean
    public IValidator<UpdateUserRequest> updateUserValidator() {
        return new UpdateUserValidator(userDataAccessor);
    }

    @Bean
    public IValidator<UpdateFinancialRecordRequest> updateFinancialRecordValidator() {
        return new UpdateFinancialRecordValidator(financialRecordAccessor);
    }

}
