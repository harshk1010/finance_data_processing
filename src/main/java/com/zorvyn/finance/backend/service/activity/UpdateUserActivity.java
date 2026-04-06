package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.model.request.UpdateUserRequest;
import com.zorvyn.finance.backend.service.model.response.UpdateUserResponse;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;

public class UpdateUserActivity
        extends AbstractActivity<UpdateUserRequest, UpdateUserResponse> {

    public UpdateUserActivity(IValidator<UpdateUserRequest> validator, IProcessor<UpdateUserRequest, UpdateUserResponse> processor) {
        super(validator, processor);
    }
}
