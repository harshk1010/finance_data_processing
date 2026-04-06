package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.model.request.UpdateFinancialRecordRequest;
import com.zorvyn.finance.backend.service.model.response.UpdateFinancialRecordResponse;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;

public class UpdateFinancialRecordActivity
        extends AbstractActivity<UpdateFinancialRecordRequest, UpdateFinancialRecordResponse> {

    public UpdateFinancialRecordActivity(IValidator<UpdateFinancialRecordRequest> validator, IProcessor<UpdateFinancialRecordRequest, UpdateFinancialRecordResponse> processor) {
        super(validator, processor);
    }
}
