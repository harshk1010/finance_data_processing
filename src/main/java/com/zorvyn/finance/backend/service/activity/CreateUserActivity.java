package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.model.request.CreateUserRequest;
import com.zorvyn.finance.backend.service.model.response.CreateUserResponse;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;

public class CreateUserActivity
        extends AbstractActivity<CreateUserRequest, CreateUserResponse> {

    public CreateUserActivity(IValidator<CreateUserRequest> validator, IProcessor<CreateUserRequest, CreateUserResponse> processor) {
        super(validator, processor);
    }
}
