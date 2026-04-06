package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.model.request.UpdateUserRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static org.apache.logging.log4j.util.Strings.isBlank;

@RequiredArgsConstructor
public class UpdateUserValidator implements IValidator<UpdateUserRequest> {

    @NonNull
    private final IUserDataAccessor userDataAccessor;

    @Override
    public void validate(UpdateUserRequest request) throws ValidationException {
        if (request == null)
            throw new ValidationException("Request must not be null");

        if (request.getId() == null)
            throw new ValidationException("User ID is required");

        if (isBlank(request.getFirstName()))
            throw new ValidationException("First name is required");

        if (isBlank(request.getLastName()))
            throw new ValidationException("Last name is required");

        if (!isBlank(request.getPassword()) && request.getPassword().length() < 8)
            throw new ValidationException("Password must be ≥ 8 characters");

        userDataAccessor.findUserById(request.getId())
                .orElseThrow(() -> new ValidationException("User not found: " + request.getId()));
    }
}
