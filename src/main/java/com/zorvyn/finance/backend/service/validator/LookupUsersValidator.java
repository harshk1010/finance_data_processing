package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.model.request.LookupUsersRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LookupUsersValidator implements IValidator<LookupUsersRequest> {

    @NonNull
    private final IUserDataAccessor userDataAccessor;

    @Override
    public void validate(LookupUsersRequest request) throws ValidationException {
        if (request == null)
            throw new ValidationException("Request must not be null");

        if (request.getPageSize() == null || request.getPageSize() < 1)
            throw new ValidationException("Page size must be at least 1");

        if (request.getPageSize() > 500) throw new ValidationException("Page size cannot exceed 500");

    }
}
