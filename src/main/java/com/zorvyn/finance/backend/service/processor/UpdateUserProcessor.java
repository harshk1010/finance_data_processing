package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.data.accessor.exception.DataAccessException;
import com.zorvyn.finance.backend.service.exception.DependencyFailure;
import com.zorvyn.finance.backend.service.model.common.User;
import com.zorvyn.finance.backend.service.model.request.UpdateUserRequest;
import com.zorvyn.finance.backend.service.model.response.UpdateUserResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UpdateUserProcessor implements IProcessor<UpdateUserRequest, UpdateUserResponse> {

    @NonNull
    private final IUserDataAccessor userDataAccessor;

    @NonNull
    private final PasswordEncoder passwordEncoder;


    @Override
    public UpdateUserResponse process(UpdateUserRequest I) throws DependencyFailure, DataAccessException {

        User existing = userDataAccessor.findUserById(I.getId())
                .orElseThrow(() -> new DependencyFailure("User not found with id: " + I.getId()));

        String password = (I.getPassword() != null && !I.getPassword().isBlank())
                ? passwordEncoder.encode(I.getPassword())
                : existing.getPassword();

        User updated = User.builder()
                .id(existing.getId())
                .email(existing.getEmail())
                .password(password)
                .firstName(I.getFirstName())
                .lastName(I.getLastName())
                .roleType(I.getRoleType() != null ? I.getRoleType() : existing.getRoleType())
                .status(existing.getStatus())
                .build();

        userDataAccessor.updateUser(updated);

        return UpdateUserResponse.builder().build();
    }
}