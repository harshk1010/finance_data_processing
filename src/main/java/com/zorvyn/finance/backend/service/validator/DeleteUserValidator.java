package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.model.request.DeleteUserRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteUserValidator implements IValidator<DeleteUserRequest> {

    @NonNull
    private final IUserDataAccessor userDataAccessor;

    @Override
    public void validate(DeleteUserRequest request) throws ValidationException {
        if (request == null)
            throw new ValidationException("Request must not be null");

        if (request.getId() == null)
            throw new ValidationException("User ID is required");

        userDataAccessor.findUserById(request.getId())
                .orElseThrow(() -> new ValidationException("User not found: " + request.getId()));

    }
}
