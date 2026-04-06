package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.model.request.SummarizeFinancialRecordsRequest;
import com.zorvyn.finance.backend.service.model.response.SummarizeFinancialRecordsResponse;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;

public class SummarizeFinancialRecordsActivity extends
        AbstractActivity<SummarizeFinancialRecordsRequest, SummarizeFinancialRecordsResponse> {

    public SummarizeFinancialRecordsActivity(IValidator<SummarizeFinancialRecordsRequest> validator, IProcessor<SummarizeFinancialRecordsRequest, SummarizeFinancialRecordsResponse> processor) {
        super(validator, processor);
    }
}
