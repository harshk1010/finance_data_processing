package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.data.accessor.exception.DataAccessException;
import com.zorvyn.finance.backend.data.constants.RoleType;
import com.zorvyn.finance.backend.data.constants.UserStatus;
import com.zorvyn.finance.backend.service.exception.DependencyFailure;
import com.zorvyn.finance.backend.service.model.common.User;
import com.zorvyn.finance.backend.service.model.request.CreateUserRequest;
import com.zorvyn.finance.backend.service.model.response.CreateUserResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CreateUserProcessor implements IProcessor<CreateUserRequest, CreateUserResponse> {

    @NonNull
    private final IUserDataAccessor userDataAccessor;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse process(CreateUserRequest I) throws DependencyFailure, DataAccessException {
        String hashedPassword = passwordEncoder.encode(I.getPassword());

        User user = User.builder()
                .email(I.getEmail())
                .password(hashedPassword)
                .firstName(I.getFirstName())
                .lastName(I.getLastName())
                .roleType(I.getRoleType() != null ? I.getRoleType() : RoleType.VIEWER)
                .status(UserStatus.ACTIVE)
                .build();

        userDataAccessor.createUser(user);

        return CreateUserResponse.builder().build();
    }
}