package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.model.request.LookupUsersRequest;
import com.zorvyn.finance.backend.service.model.response.LookupUsersResponse;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;

public class LookupUsersActivity
        extends AbstractActivity<LookupUsersRequest, LookupUsersResponse> {

    public LookupUsersActivity(IValidator<LookupUsersRequest> validator, IProcessor<LookupUsersRequest, LookupUsersResponse> processor) {
        super(validator, processor);
    }
}
