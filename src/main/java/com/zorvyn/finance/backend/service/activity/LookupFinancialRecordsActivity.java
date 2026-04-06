package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.model.request.LookupFinancialRecordsRequest;
import com.zorvyn.finance.backend.service.model.response.LookupFinancialRecordsResponse;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;

public class LookupFinancialRecordsActivity extends
        AbstractActivity<LookupFinancialRecordsRequest, LookupFinancialRecordsResponse> {

    public LookupFinancialRecordsActivity(IValidator<LookupFinancialRecordsRequest> validator, IProcessor<LookupFinancialRecordsRequest, LookupFinancialRecordsResponse> processor) {
        super(validator, processor);
    }
}
