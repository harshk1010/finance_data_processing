package com.zorvyn.finance.backend.service.model.request;

import com.zorvyn.finance.backend.data.constants.RecordCategory;
import com.zorvyn.finance.backend.data.constants.RecordType;
import com.zorvyn.finance.backend.data.constants.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Builder
@Getter
public class UpdateFinancialRecordRequest extends BaseRequest {

    @NonNull
    private Long id;

    @NonNull
    private BigDecimal amount;

    @NonNull
    private RecordType type;

    @NonNull
    private RecordCategory category;

    private String description;

}
