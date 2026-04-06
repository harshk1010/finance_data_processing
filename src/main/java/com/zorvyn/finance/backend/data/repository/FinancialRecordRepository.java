package com.zorvyn.finance.backend.data.repository;

import com.zorvyn.finance.backend.data.constants.RecordCategory;
import com.zorvyn.finance.backend.data.constants.RecordType;
import com.zorvyn.finance.backend.data.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    Optional<FinancialRecord> findByIdAndDeletedFalse(Long id);

    @Query("""
    SELECT r FROM FinancialRecord r
    WHERE r.deleted = false
      AND (:#{#types == null || #types.isEmpty()} = true OR r.type IN :types)
      AND (:#{#categories == null || #categories.isEmpty()} = true OR r.category IN :categories)
      AND (:minAmt IS NULL OR r.amount >= :minAmt)
      AND (:maxAmt IS NULL OR r.amount <= :maxAmt)
      AND (CAST(:startTime AS timestamp) IS NULL OR r.transactionTime >= :startTime)
      AND (CAST(:endTime AS timestamp) IS NULL OR r.transactionTime <= :endTime)
      AND (:lastKey IS NULL OR r.id > :lastKey)
    ORDER BY r.id ASC
    """)
    List<FinancialRecord> findWithFilters(
            @Param("types") List<RecordType> types,
            @Param("categories") List<RecordCategory> categories,
            @Param("minAmt") BigDecimal minAmt,
            @Param("maxAmt") BigDecimal maxAmt,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("pageSize") int pageSize,
            @Param("lastKey") Long lastAccessedKey
    );

    @Query("""
    SELECT COALESCE(SUM(r.amount), 0)
    FROM FinancialRecord r
    WHERE r.deleted = false
      AND r.type = :type
      AND (CAST(:startTime AS timestamp) IS NULL OR r.transactionTime >= :startTime)
      AND (CAST(:endTime AS timestamp) IS NULL OR r.transactionTime <= :endTime)
    """)
    BigDecimal sumAmountByType(
            @Param("type") RecordType type,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
    SELECT COALESCE(SUM(r.amount), 0)
    FROM FinancialRecord r
    WHERE r.deleted = false
      AND r.category = :category
      AND (CAST(:startTime AS timestamp) IS NULL OR r.transactionTime >= :startTime)
      AND (CAST(:endTime AS timestamp) IS NULL OR r.transactionTime <= :endTime)
    """)
    BigDecimal sumAmountByCategory(
            @Param("category") RecordCategory category,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
    SELECT r FROM FinancialRecord r
    WHERE r.deleted = false
    ORDER BY r.transactionTime DESC
    LIMIT :limit
    """)
    List<FinancialRecord> findTopRecent(@Param("limit") int limit);

    @Query("""
    SELECT r FROM FinancialRecord r
    WHERE r.deleted = false
      AND r.transactionTime >= :startTime
      AND r.transactionTime <= :endTime
    ORDER BY r.transactionTime ASC
    """)
    List<FinancialRecord> findAllInRange(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}