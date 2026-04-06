package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.model.request.CreateFinancialRecordRequest;
import com.zorvyn.finance.backend.service.model.response.CreateFinancialRecordResponse;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;

public class CreateFinancialRecordActivity
        extends AbstractActivity<CreateFinancialRecordRequest, CreateFinancialRecordResponse> {


    public CreateFinancialRecordActivity(IValidator<CreateFinancialRecordRequest> validator, IProcessor<CreateFinancialRecordRequest, CreateFinancialRecordResponse> processor) {
        super(validator, processor);
    }
}
