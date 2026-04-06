package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.service.model.common.FinancialRecord;
import com.zorvyn.finance.backend.service.model.request.UpdateFinancialRecordRequest;
import com.zorvyn.finance.backend.service.model.response.UpdateFinancialRecordResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UpdateFinancialRecordProcessor implements
        IProcessor<UpdateFinancialRecordRequest, UpdateFinancialRecordResponse> {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    @Override
    public UpdateFinancialRecordResponse process(UpdateFinancialRecordRequest request) {
        FinancialRecord record = FinancialRecord.builder()
                .id(request.getId()).amount(request.getAmount())
                .type(request.getType()).category(request.getCategory())
                .description(request.getDescription())
                .transactionTime(LocalDateTime.now()).build();
        financialRecordAccessor.updateFinancialRecord(record);
        return UpdateFinancialRecordResponse.builder().build();
    }
}
