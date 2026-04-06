package com.zorvyn.finance.backend.service.model.response;

import com.zorvyn.finance.backend.service.model.common.FinancialRecord;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LookupFinancialRecordsResponse extends BaseResponse {

    private List<FinancialRecord> financialRecords;

    private Long lastAccessedKey;

}
