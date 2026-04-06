package com.zorvyn.finance.backend.data.accessor;

import com.zorvyn.finance.backend.data.constants.RecordCategory;
import com.zorvyn.finance.backend.data.constants.RecordType;
import com.zorvyn.finance.backend.service.model.common.FinancialRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IFinancialRecordAccessor {

    void createFinancialRecord(FinancialRecord financialRecord);

    Optional<FinancialRecord> findById(Long id);

    List<FinancialRecord> findRecords(List<RecordType> types, List<RecordCategory> categories,
                                      BigDecimal minAmt, BigDecimal maxAmt, LocalDateTime startTime, LocalDateTime endTime,
                                      int pageSize, Long lastAccessedKey);

    void updateFinancialRecord(FinancialRecord financialRecord);

    void deleteFinancialRecord(Long id);

    BigDecimal sumByType(RecordType type, LocalDateTime startTime, LocalDateTime endTime);

    BigDecimal sumByCategory(RecordCategory category, LocalDateTime startTime, LocalDateTime endTime);

    List<FinancialRecord> findRecent(int limit);

    List<FinancialRecord> findAllInRange(LocalDateTime startTime, LocalDateTime endTime);

}