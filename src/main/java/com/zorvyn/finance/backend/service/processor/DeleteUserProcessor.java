package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.IUserDataAccessor;
import com.zorvyn.finance.backend.service.model.request.DeleteUserRequest;
import com.zorvyn.finance.backend.service.model.response.DeleteUserResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteUserProcessor implements IProcessor<DeleteUserRequest, DeleteUserResponse> {

    @NonNull
    private final IUserDataAccessor userDataAccessor;

    @Override
    public DeleteUserResponse process(DeleteUserRequest request) {
        userDataAccessor.deleteUser(request.getId());
        return DeleteUserResponse.builder().build();
    }
}
