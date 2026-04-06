package com.zorvyn.finance.backend.module;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.service.model.request.*;
import com.zorvyn.finance.backend.service.model.response.*;
import com.zorvyn.finance.backend.service.processor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ProcessorModule {

    @Bean
    public IProcessor<CreateUserRequest, CreateUserResponse> createUserProcessor(
            IUserDataAccessor userDataAccessor, PasswordEncoder passwordEncoder) {
        return new CreateUserProcessor(userDataAccessor, passwordEncoder);
    }

    @Bean
    public IProcessor<UpdateUserRequest, UpdateUserResponse> updateUserProcessor(
            IUserDataAccessor userDataAccessor, PasswordEncoder passwordEncoder) {
        return new UpdateUserProcessor(userDataAccessor, passwordEncoder);
    }

    @Bean
    public IProcessor<DeleteUserRequest, DeleteUserResponse> deleteUserProcessor(
            IUserDataAccessor userDataAccessor) {
        return new DeleteUserProcessor(userDataAccessor);
    }

    @Bean
    public IProcessor<LookupUsersRequest, LookupUsersResponse> lookupUsersProcessor(
            IUserDataAccessor userDataAccessor) {
        return new LookupUsersProcessor(userDataAccessor);
    }

    @Bean
    public IProcessor<CreateFinancialRecordRequest, CreateFinancialRecordResponse> createFinancialRecordProcessor(
            IFinancialRecordAccessor accessor) {
        return new CreateFinancialRecordProcessor(accessor);
    }

    @Bean
    public IProcessor<UpdateFinancialRecordRequest, UpdateFinancialRecordResponse> updateFinancialRecordProcessor(
            IFinancialRecordAccessor accessor) {
        return new UpdateFinancialRecordProcessor(accessor);
    }

    @Bean
    public IProcessor<DeleteFinancialRecordRequest, DeleteFinancialRecordResponse> deleteFinancialRecordProcessor(
            IFinancialRecordAccessor accessor) {
        return new DeleteFinancialRecordProcessor(accessor);
    }

    @Bean
    public IProcessor<LookupFinancialRecordsRequest, LookupFinancialRecordsResponse> lookupFinancialRecordsProcessor(
            IFinancialRecordAccessor accessor) {
        return new LookupFinancialRecordsProcessor(accessor);
    }

    @Bean
    public IProcessor<SummarizeFinancialRecordsRequest, SummarizeFinancialRecordsResponse> summarizeFinancialRecordsProcessor(
            IFinancialRecordAccessor accessor) {
        return new SummarizeFinancialRecordsProcessor(accessor);
    }
}