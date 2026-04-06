package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.model.request.UpdateFinancialRecordRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UpdateFinancialRecordValidator implements IValidator<UpdateFinancialRecordRequest> {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    @Override
    public void validate(UpdateFinancialRecordRequest request) throws ValidationException {
        if (request == null)
            throw new ValidationException("Request must not be null");

        if (request.getId() == null)
            throw new ValidationException("Record ID is required");

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Valid amount is required");

        if (request.getType() == null)
            throw new ValidationException("Record type is required");

        if (request.getCategory() == null)
            throw new ValidationException("Category is required");

        financialRecordAccessor.findById(request.getId())
                .orElseThrow(() -> new ValidationException("Record not found: " + request.getId()));
    }
}
