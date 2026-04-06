package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.model.request.CreateFinancialRecordRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreateFinancialRecordValidator implements IValidator<CreateFinancialRecordRequest> {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    @Override
    public void validate(CreateFinancialRecordRequest request) throws ValidationException {
        if (request == null)
            throw new ValidationException("Request must not be null");

        if (request.getAmount() == null)
            throw new ValidationException("Amount is required");

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Amount must be greater than zero");

        if (request.getType() == null)
            throw new ValidationException("Record type is required");

        if (request.getCategory() == null)
            throw new ValidationException("Category is required");

        if (request.getTransactionTime() == null)
            throw new ValidationException("Transaction time is required");

        if (request.getDescription() != null && request.getDescription().length() > 500)
            throw new ValidationException("Description exceeds 500 characters");

    }
}
