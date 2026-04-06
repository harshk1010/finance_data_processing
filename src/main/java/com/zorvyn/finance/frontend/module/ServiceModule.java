package com.zorvyn.finance.frontend.module;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.service.FinanceService;
import com.zorvyn.finance.backend.service.activity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ServiceModule {

    @Bean
    public FinanceService financeService(
            CreateUserActivity createUserActivity,
            CreateFinancialRecordActivity createFinancialRecordActivity,
            LookupUsersActivity lookupUsersActivity,
            LookupFinancialRecordsActivity lookupFinancialRecordsActivity,
            UpdateUserActivity updateUserActivity,
            UpdateFinancialRecordActivity updateFinancialRecordActivity,
            DeleteUserActivity deleteUserActivity,
            DeleteFinancialRecordActivity deleteFinancialRecordActivity,
            SummarizeFinancialRecordsActivity summarizeFinancialRecordsActivity,
            IUserDataAccessor userAccessor
    ) {
        return FinanceService.builder()
                .createUserActivity(createUserActivity)
                .createFinancialRecordActivity(createFinancialRecordActivity)
                .lookupUserActivity(lookupUsersActivity)
                .lookupFinancialRecordsActivity(lookupFinancialRecordsActivity)
                .updateUserActivity(updateUserActivity)
                .updateFinancialRecordActivity(updateFinancialRecordActivity)
                .deleteUserActivity(deleteUserActivity)
                .deleteFinancialRecordActivity(deleteFinancialRecordActivity)
                .summarizeFinancialRecordsActivity(summarizeFinancialRecordsActivity)
                .userAccessor(userAccessor)
                .build();
    }
}
