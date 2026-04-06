package com.zorvyn.finance.backend.service.model.common;

import com.zorvyn.finance.backend.data.constants.RecordCategory;
import com.zorvyn.finance.backend.data.constants.RecordType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class FinancialRecord {

    private Long id;

    @NonNull
    private BigDecimal amount;

    @NonNull
    private RecordType type;

    @NonNull
    private RecordCategory category;

    @NonNull
    private LocalDateTime transactionTime;

    private String description;

}
