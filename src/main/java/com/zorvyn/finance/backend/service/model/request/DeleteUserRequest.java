package com.zorvyn.finance.backend.service.model.request;

import com.zorvyn.finance.backend.data.constants.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class DeleteUserRequest extends BaseRequest {

    @NonNull
    private Long id;

    private RoleType roleType;
}
