package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.service.model.request.DeleteFinancialRecordRequest;
import com.zorvyn.finance.backend.service.model.response.DeleteFinancialRecordResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteFinancialRecordProcessor implements
        IProcessor<DeleteFinancialRecordRequest, DeleteFinancialRecordResponse> {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    @Override
    public DeleteFinancialRecordResponse process(DeleteFinancialRecordRequest request) {
        financialRecordAccessor.deleteFinancialRecord(request.getId());
        return DeleteFinancialRecordResponse.builder().build();
    }
}
