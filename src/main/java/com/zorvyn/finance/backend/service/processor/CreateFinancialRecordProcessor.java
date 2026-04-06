package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.service.model.common.FinancialRecord;
import com.zorvyn.finance.backend.service.model.request.CreateFinancialRecordRequest;
import com.zorvyn.finance.backend.service.model.response.CreateFinancialRecordResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateFinancialRecordProcessor
        implements IProcessor<CreateFinancialRecordRequest, CreateFinancialRecordResponse> {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    @Override
    public CreateFinancialRecordResponse process(CreateFinancialRecordRequest request) {
        FinancialRecord record = FinancialRecord.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .description(request.getDescription())
                .transactionTime(request.getTransactionTime())
                .build();
        financialRecordAccessor.createFinancialRecord(record);
        return CreateFinancialRecordResponse.builder().build();
    }
}
