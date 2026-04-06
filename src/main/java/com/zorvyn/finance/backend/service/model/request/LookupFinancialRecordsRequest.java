package com.zorvyn.finance.backend.service.model.request;

import com.zorvyn.finance.backend.data.constants.RecordCategory;
import com.zorvyn.finance.backend.data.constants.RecordType;
import com.zorvyn.finance.backend.data.constants.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class LookupFinancialRecordsRequest extends BaseRequest {

    private List<RecordType> recordTypes;

    private List<RecordCategory> recordCategories;

    private BigDecimal minAmt;

    private BigDecimal maxAmt;

    private LocalDateTime startTime;

    private LocalDateTime endTime ;

    @Builder.Default
    private Integer pageSize = 100;

    private Long lastAccessedKey;

    private RoleType roleType;

    @Getter
    public enum FinancialRecordLookupFilterType {
        TYPE,
        CATEGORY,
        AMOUNT_RANGE,
        TRANSACTION_TIME_RANGE;
    }

}
