package com.zorvyn.finance.backend.service.validator;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.service.exception.ValidationException;
import com.zorvyn.finance.backend.service.model.request.CreateUserRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static org.apache.logging.log4j.util.Strings.isBlank;

@RequiredArgsConstructor
public class CreateUserValidator implements IValidator<CreateUserRequest> {

    @NonNull
    private final IUserDataAccessor userDataAccessor;

    @Override
    public void validate(CreateUserRequest request) throws ValidationException {
        if (request == null)
            throw new ValidationException("Request must not be null");

        if (isBlank(request.getEmail()))
            throw new ValidationException("Email is required");

        if (!request.getEmail().matches("^[\\w.+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$"))
            throw new ValidationException("Email format is invalid");

        if (isBlank(request.getPassword()))
            throw new ValidationException("Password is required");

        if (request.getPassword().length() < 8)
            throw new ValidationException("Password must be ≥ 8 characters");

        if (isBlank(request.getFirstName()))
            throw new ValidationException("First name is required");

        if (isBlank(request.getLastName()))
            throw new ValidationException("Last name is required");

        if (request.getRoleType() == null)
            throw new ValidationException("Role type is required");

        if (userDataAccessor.existsByEmail(request.getEmail()))
            throw new ValidationException("Email already registered");

    }
}
