package com.zorvyn.finance.backend.service.model.response;

import com.zorvyn.finance.backend.service.model.common.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LookupUsersResponse extends BaseResponse {

    private List<User> users;

    private Long lastAccessedKey;

}
