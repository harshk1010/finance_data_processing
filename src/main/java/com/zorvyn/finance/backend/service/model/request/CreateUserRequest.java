package com.zorvyn.finance.backend.service.model.request;

import com.zorvyn.finance.backend.data.constants.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUserRequest extends BaseRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private RoleType roleType;

}
