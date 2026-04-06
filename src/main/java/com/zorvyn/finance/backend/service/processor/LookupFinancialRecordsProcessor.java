package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.service.model.common.FinancialRecord;
import com.zorvyn.finance.backend.service.model.request.LookupFinancialRecordsRequest;
import com.zorvyn.finance.backend.service.model.response.LookupFinancialRecordsResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LookupFinancialRecordsProcessor
        implements IProcessor<LookupFinancialRecordsRequest, LookupFinancialRecordsResponse> {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    @Override
    public LookupFinancialRecordsResponse process(LookupFinancialRecordsRequest request) {
        List<FinancialRecord> records = financialRecordAccessor.findRecords(
                request.getRecordTypes(), request.getRecordCategories(),
                request.getMinAmt(), request.getMaxAmt(),
                request.getStartTime(), request.getEndTime(),
                request.getPageSize(), request.getLastAccessedKey());
        Long lastKey = records.isEmpty() ? null : records.getLast().getId();
        return LookupFinancialRecordsResponse.builder()
                .financialRecords(records).lastAccessedKey(lastKey).build();
    }
}
