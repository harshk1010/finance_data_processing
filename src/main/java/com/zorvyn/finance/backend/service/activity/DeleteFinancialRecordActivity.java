package com.zorvyn.finance.backend.service.activity;

import com.zorvyn.finance.backend.service.model.request.DeleteFinancialRecordRequest;
import com.zorvyn.finance.backend.service.model.response.DeleteFinancialRecordResponse;
import com.zorvyn.finance.backend.service.processor.IProcessor;
import com.zorvyn.finance.backend.service.validator.IValidator;

public class DeleteFinancialRecordActivity
        extends AbstractActivity<DeleteFinancialRecordRequest, DeleteFinancialRecordResponse> {

    public DeleteFinancialRecordActivity(IValidator<DeleteFinancialRecordRequest> validator, IProcessor<DeleteFinancialRecordRequest, DeleteFinancialRecordResponse> processor) {
        super(validator, processor);
    }
}
