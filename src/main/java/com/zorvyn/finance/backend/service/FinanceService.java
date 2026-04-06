package com.zorvyn.finance.backend.service;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.data.constants.RoleType;
import com.zorvyn.finance.backend.data.constants.UserStatus;
import com.zorvyn.finance.backend.service.activity.*;
import com.zorvyn.finance.backend.service.model.common.User;
import com.zorvyn.finance.backend.service.model.request.*;
import com.zorvyn.finance.backend.service.model.response.*;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Builder
public class FinanceService {

    private CreateFinancialRecordActivity createFinancialRecordActivity;

    private CreateUserActivity createUserActivity;

    private DeleteFinancialRecordActivity deleteFinancialRecordActivity;

    private DeleteUserActivity deleteUserActivity;

    private LookupFinancialRecordsActivity lookupFinancialRecordsActivity;

    private LookupUsersActivity lookupUserActivity;

    private UpdateFinancialRecordActivity updateFinancialRecordActivity;

    private UpdateUserActivity updateUserActivity;

    private SummarizeFinancialRecordsActivity summarizeFinancialRecordsActivity;

    private final IUserDataAccessor userAccessor;

    public CreateFinancialRecordResponse createFinancialRecord(
            CreateFinancialRecordRequest request,
            String email) {

        User user = userAccessor.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return createFinancialRecordActivity.execute(request);
    }

    public CreateUserResponse createUser(CreateUserRequest request) {

        return createUserActivity.execute(request);
    }

    public DeleteFinancialRecordResponse
                deleteFinancialRecord(DeleteFinancialRecordRequest request) {
        return deleteFinancialRecordActivity.execute(request);
    }

    public DeleteUserResponse deleteUser(DeleteUserRequest request) {
        return deleteUserActivity.execute(request);
    }

    public LookupFinancialRecordsResponse
                lookupFinancialRecords(LookupFinancialRecordsRequest request) {
        return lookupFinancialRecordsActivity.execute(request);
    }

    public LookupUsersResponse lookupUser(LookupUsersRequest request) {
        return lookupUserActivity.execute(request);
    }

    public UpdateFinancialRecordResponse
                updateFinancialRecord(UpdateFinancialRecordRequest request) {
        return updateFinancialRecordActivity.execute(request);
    }

    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        return updateUserActivity.execute(request);
    }

    public SummarizeFinancialRecordsResponse
                summarizeFinancialRecords(SummarizeFinancialRecordsRequest request) {
        return summarizeFinancialRecordsActivity.execute(request);
    }

    public Optional<User> findUserByEmail(String email) {

        return userAccessor.findUserByEmail(email);
    }

}
