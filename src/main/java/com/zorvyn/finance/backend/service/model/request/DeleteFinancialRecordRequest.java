package com.zorvyn.finance.backend.service.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class DeleteFinancialRecordRequest extends BaseRequest {

    @NonNull
    private Long id;

}
