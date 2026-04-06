package com.zorvyn.finance.backend.service.model.common;

import com.zorvyn.finance.backend.data.constants.RoleType;
import com.zorvyn.finance.backend.data.constants.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@ToString
public class User {

    private Long id;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private RoleType roleType;

    @NonNull
    private UserStatus status;


}
