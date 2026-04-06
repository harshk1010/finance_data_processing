package com.zorvyn.finance.backend.service.model.request;

import com.zorvyn.finance.backend.data.constants.RoleType;
import com.zorvyn.finance.backend.data.constants.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class UpdateUserRequest extends BaseRequest {

    @NonNull
    private Long id;

    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private RoleType roleType;

}
