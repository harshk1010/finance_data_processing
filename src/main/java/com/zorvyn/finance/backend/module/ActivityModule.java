package com.zorvyn.finance.backend.module;

import com.zorvyn.finance.backend.service.activity.*;
import com.zorvyn.finance.backend.service.model.request.*;
import com.zorvyn.finance.backend.service.model.response.*;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ActivityModule {

    @Bean
    public CreateUserActivity createUsersActivity(
            IValidator<CreateUserRequest> validator,
            IProcessor<CreateUserRequest, CreateUserResponse> processor
    ) {
        return new CreateUserActivity(validator, processor);
    }

    @Bean
    public CreateFinancialRecordActivity createFinancialRecordActivity(
            IValidator<CreateFinancialRecordRequest> validator,
            IProcessor<CreateFinancialRecordRequest, CreateFinancialRecordResponse> processor
    ) {
        return new CreateFinancialRecordActivity(validator, processor);
    }

    @Bean
    public UpdateUserActivity updateUserActivity(
            IValidator<UpdateUserRequest> validator,
            IProcessor<UpdateUserRequest, UpdateUserResponse> processor
    ) {
        return new UpdateUserActivity(validator, processor);
    }

    @Bean
    public UpdateFinancialRecordActivity updateFinancialRecordActivity(
            IValidator<UpdateFinancialRecordRequest> validator,
            IProcessor<UpdateFinancialRecordRequest, UpdateFinancialRecordResponse> processor
    ) {
        return new UpdateFinancialRecordActivity(validator, processor);
    }

    @Bean
    public DeleteUserActivity deleteUserActivity(
            IValidator<DeleteUserRequest> validator,
            IProcessor<DeleteUserRequest, DeleteUserResponse> processor
    ) {
        return new DeleteUserActivity(validator, processor);
    }

    @Bean
    public DeleteFinancialRecordActivity deleteFinancialRecordActivity(
            IValidator<DeleteFinancialRecordRequest> validator,
            IProcessor<DeleteFinancialRecordRequest, DeleteFinancialRecordResponse> processor
    ) {
        return new DeleteFinancialRecordActivity(validator, processor);
    }

    @Bean
    public LookupUsersActivity lookupUsersActivity(
            IValidator<LookupUsersRequest> validator,
            IProcessor<LookupUsersRequest, LookupUsersResponse> processor
    ) {
        return new LookupUsersActivity(validator, processor);
    }

    @Bean
    public LookupFinancialRecordsActivity lookupFinancialRecordActivity(
            IValidator<LookupFinancialRecordsRequest> validator,
            IProcessor<LookupFinancialRecordsRequest, LookupFinancialRecordsResponse> processor
    ) {
        return new LookupFinancialRecordsActivity(validator, processor);
    }

    @Bean
    public SummarizeFinancialRecordsActivity summarizeFinancialRecordsActivity(
            IValidator<SummarizeFinancialRecordsRequest> validator,
            IProcessor<SummarizeFinancialRecordsRequest, SummarizeFinancialRecordsResponse> processor
    ) {
        return new SummarizeFinancialRecordsActivity(validator, processor);
    }

}
