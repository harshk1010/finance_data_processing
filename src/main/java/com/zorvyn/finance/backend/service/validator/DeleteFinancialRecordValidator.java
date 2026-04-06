package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.model.request.DeleteFinancialRecordRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteFinancialRecordValidator implements IValidator<DeleteFinancialRecordRequest> {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    @Override
    public void validate(DeleteFinancialRecordRequest request) throws ValidationException {
        if (request == null)
            throw new ValidationException("Request must not be null");

        if (request.getId() == null)
            throw new ValidationException("Record ID is required");

        financialRecordAccessor.findById(request.getId())
                .orElseThrow(() -> new ValidationException("Record not found: " + request.getId()));
    }
}
