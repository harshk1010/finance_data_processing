package com.zorvyn.finance.backend.data.accessor.impl;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.data.accessor.exception.DataAccessException;
import com.zorvyn.finance.backend.data.accessor.transformer.ServiceToDataModelTransformer;
import com.zorvyn.finance.backend.data.constants.RecordCategory;
import com.zorvyn.finance.backend.data.constants.RecordType;
import com.zorvyn.finance.backend.data.repository.FinancialRecordRepository;
import com.zorvyn.finance.backend.service.model.common.FinancialRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class FinancialRecordSqlAccessor implements IFinancialRecordAccessor {

    private final FinancialRecordRepository recordRepository;
    private final ServiceToDataModelTransformer transformer;

    @Override
    public void createFinancialRecord(FinancialRecord r) {
        try {
            recordRepository.save(transformer.toFinancialRecordEntity(r));
        } catch (Exception e) {
            throw new DataAccessException("Failed to create financial record: " + e.getMessage());
        }
    }

    @Override
    public Optional<FinancialRecord> findById(Long id) {
        return recordRepository.findByIdAndDeletedFalse(id)
                .map(transformer::toServiceFinancialRecord);
    }

    @Override
    public List<FinancialRecord> findRecords(List<RecordType> types, List<RecordCategory> categories,
                                             BigDecimal minAmt, BigDecimal maxAmt, LocalDateTime startTime, LocalDateTime endTime,
                                             int pageSize, Long lastKey) {
        return recordRepository.findWithFilters(types, categories, minAmt, maxAmt,
                        startTime, endTime, pageSize, lastKey)
                .stream().map(transformer::toServiceFinancialRecord).toList();
    }

    @Override
    public void updateFinancialRecord(FinancialRecord r) {
        var existing = recordRepository.findByIdAndDeletedFalse(r.getId())
                .orElseThrow(() -> new DataAccessException("Record not found: " + r.getId()));
        existing.setAmount(r.getAmount());
        existing.setType(r.getType());
        existing.setCategory(r.getCategory());
        existing.setDescription(r.getDescription());
        recordRepository.save(existing);
    }

    @Override
    public void deleteFinancialRecord(Long id) {
        var existing = recordRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new DataAccessException("Record not found: " + id));
        existing.setDeleted(true);
        recordRepository.save(existing);
    }

    @Override
    public BigDecimal sumByType(RecordType type, LocalDateTime startTime, LocalDateTime endTime) {
        BigDecimal result = recordRepository.sumAmountByType(type, startTime, endTime);
        return result != null ? result : BigDecimal.ZERO;
    }
    @Override
    public BigDecimal sumByCategory(RecordCategory category, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            BigDecimal result = recordRepository.sumAmountByCategory(category, startTime, endTime);
            return result != null ? result : BigDecimal.ZERO;
        } catch (Exception e) {
            log.error("Failed to sum by category {}: {}", category, e.getMessage());
            throw new DataAccessException("Failed to sum by category: " + e.getMessage());
        }
    }

    @Override
    public List<FinancialRecord> findRecent(int limit) {
        try {
            return recordRepository.findTopRecent(limit)
                    .stream().map(transformer::toServiceFinancialRecord).toList();
        } catch (Exception e) {
            log.error("Failed to fetch recent records: {}", e.getMessage());
            throw new DataAccessException("Failed to fetch recent records: " + e.getMessage());
        }
    }

    @Override
    public List<FinancialRecord> findAllInRange(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return recordRepository.findAllInRange(startTime, endTime)
                    .stream().map(transformer::toServiceFinancialRecord).toList();
        } catch (Exception e) {
            log.error("Failed to fetch records in range: {}", e.getMessage());
            throw new DataAccessException("Failed to fetch records in range: " + e.getMessage());
        }
    }
}