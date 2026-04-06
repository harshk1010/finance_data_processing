package com.zorvyn.finance.backend.service.model.request;

import com.zorvyn.finance.backend.data.constants.RecordCategory;
import com.zorvyn.finance.backend.data.constants.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class SummarizeFinancialRecordsRequest extends BaseRequest {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private RecordCategory recordCategory;

    private RoleType roleType;

}
