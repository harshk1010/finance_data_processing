package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.model.request.SummarizeFinancialRecordsRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SummarizeFinancialRecordsValidator implements IValidator<SummarizeFinancialRecordsRequest> {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    @Override
    public void validate(SummarizeFinancialRecordsRequest request) throws ValidationException {
        if (request == null)
            throw new ValidationException("Request must not be null");

        if (request.getStartTime() != null && request.getEndTime() != null
                && request.getStartTime().isAfter(request.getEndTime()))
            throw new ValidationException("startTime must be before endTime");
    }
}
