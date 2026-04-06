package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.model.request.LookupFinancialRecordsRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LookupFinancialRecordsValidator implements IValidator<LookupFinancialRecordsRequest>  {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    @Override
    public void validate(LookupFinancialRecordsRequest request) throws ValidationException {
        if (request == null)
            throw new ValidationException("Request must not be null");

        if (request.getPageSize() == null || request.getPageSize() < 1)
            throw new ValidationException("Page size must be at least 1");

        if (request.getPageSize() > 500)
            throw new ValidationException("Page size cannot exceed 500");

        if (request.getMinAmt() != null && request.getMaxAmt() != null
                && request.getMinAmt().compareTo(request.getMaxAmt()) > 0)
            throw new ValidationException("minAmt must be <= maxAmt");

        if (request.getStartTime() != null && request.getEndTime() != null
                && request.getStartTime().isAfter(request.getEndTime()))
            throw new ValidationException("startTime must be before endTime");
    }
}
