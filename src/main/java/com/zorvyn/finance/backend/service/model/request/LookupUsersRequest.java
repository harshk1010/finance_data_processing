package com.zorvyn.finance.backend.service.model.request;

import com.zorvyn.finance.backend.data.constants.RoleType;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class LookupUsersRequest extends BaseRequest {

    private String emailId;

    private List<RoleType> roleTypes;

    @Builder.Default
    private Integer pageSize = 100;

    private Long lastAccessedKey;

    @Getter
    public enum UserLookupFilterType {
        EMAIL,
        ROLE
    }

}
