package com.zorvyn.finance.backend.module;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.data.accessor.impl.FinancialRecordSqlAccessor;
import com.zorvyn.finance.backend.data.accessor.impl.UserDataSqlAccessor;
import com.zorvyn.finance.backend.data.accessor.transformer.ServiceToDataModelTransformer;
import com.zorvyn.finance.backend.data.repository.FinancialRecordRepository;
import com.zorvyn.finance.backend.data.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataAccessModule {

    @NonNull private final UserRepository userRepository;
    @NonNull private final FinancialRecordRepository financialRecordRepository;

    @Bean
    public ServiceToDataModelTransformer serviceToDataModelTransformer() {
        return new ServiceToDataModelTransformer();
    }

    @Bean
    public IUserDataAccessor userDataAccessor(ServiceToDataModelTransformer transformer) {
        return new UserDataSqlAccessor(userRepository, transformer);
    }

    @Bean
    public IFinancialRecordAccessor financialRecordAccessor(ServiceToDataModelTransformer transformer) {
        return new FinancialRecordSqlAccessor(financialRecordRepository, transformer);
    }
}