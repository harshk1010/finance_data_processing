package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.service.model.request.LookupUsersRequest;
import com.zorvyn.finance.backend.service.model.response.LookupUsersResponse;
import com.zorvyn.finance.backend.service.model.common.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LookupUsersProcessor
        implements IProcessor<LookupUsersRequest, LookupUsersResponse> {

    @NonNull
    private final IUserDataAccessor userDataAccessor;

    @Override
    public LookupUsersResponse process(LookupUsersRequest request) {
        List<User> users = userDataAccessor.findUsers(
                request.getEmailId(), request.getRoleTypes(),
                request.getPageSize(), request.getLastAccessedKey());
        Long lastKey = users.isEmpty() ? null : users.getLast().getId();
        return LookupUsersResponse.builder()
                .users(users).lastAccessedKey(lastKey).build();
    }
}
