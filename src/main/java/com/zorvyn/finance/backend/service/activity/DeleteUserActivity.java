package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.model.request.DeleteUserRequest;
import com.zorvyn.finance.backend.service.model.response.DeleteUserResponse;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;

public class DeleteUserActivity
        extends AbstractActivity<DeleteUserRequest, DeleteUserResponse> {

    public DeleteUserActivity(IValidator<DeleteUserRequest> validator, IProcessor<DeleteUserRequest, DeleteUserResponse> processor) {
        super(validator, processor);
    }
}
