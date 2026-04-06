package com.zorvyn.finance.backend.service.processor;

import com.zorvyn.finance.backend.data.accessor.IFinancialRecordAccessor;
import com.zorvyn.finance.backend.data.accessor.exception.DataAccessException;
import com.zorvyn.finance.backend.data.constants.RecordCategory;
import com.zorvyn.finance.backend.data.constants.RecordType;
import com.zorvyn.finance.backend.service.exception.DependencyFailure;
import com.zorvyn.finance.backend.service.model.common.FinancialRecord;
import com.zorvyn.finance.backend.service.model.request.SummarizeFinancialRecordsRequest;
import com.zorvyn.finance.backend.service.model.response.SummarizeFinancialRecordsResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SummarizeFinancialRecordsProcessor
        implements IProcessor<SummarizeFinancialRecordsRequest, SummarizeFinancialRecordsResponse> {

    @NonNull
    private final IFinancialRecordAccessor financialRecordAccessor;

    private static final int RECENT_ACTIVITY_LIMIT = 5;
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    @Override
    public SummarizeFinancialRecordsResponse process(SummarizeFinancialRecordsRequest request)
            throws DependencyFailure, DataAccessException {

        LocalDateTime start = request.getStartTime();
        LocalDateTime end = request.getEndTime();

        BigDecimal totalIncome = financialRecordAccessor.sumByType(RecordType.INCOME, start, end);
        BigDecimal totalExpenses = financialRecordAccessor.sumByType(RecordType.EXPENSE, start, end);

        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        Map<RecordCategory, BigDecimal> categoryTotals = new LinkedHashMap<>();
        for (RecordCategory category : RecordCategory.values()) {
            BigDecimal total = financialRecordAccessor.sumByCategory(category, start, end);
            if (total.compareTo(BigDecimal.ZERO) > 0) {
                categoryTotals.put(category, total);
            }
        }

        List<FinancialRecord> recent = financialRecordAccessor.findRecent(RECENT_ACTIVITY_LIMIT);
        List<SummarizeFinancialRecordsResponse.RecentActivity> recentActivity = recent.stream()
                .map(r -> SummarizeFinancialRecordsResponse.RecentActivity.builder()
                        .id(r.getId())
                        .amount(r.getAmount())
                        .type(r.getType().name())
                        .category(r.getCategory().name())
                        .transactionTime(r.getTransactionTime().toString())
                        .description(r.getDescription())
                        .build())
                .toList();


        LocalDateTime trendStart = start != null ? start : LocalDateTime.now().minusMonths(6);
        LocalDateTime trendEnd = end != null ? end : LocalDateTime.now();

        List<FinancialRecord> allInRange = financialRecordAccessor.findAllInRange(trendStart, trendEnd);

        Map<String, SummarizeFinancialRecordsResponse.MonthlyTrend> monthlyTrends =
                buildMonthlyTrends(allInRange, trendStart, trendEnd);

        return SummarizeFinancialRecordsResponse.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .categoryTotals(categoryTotals)
                .recentActivity(recentActivity)
                .monthlyTrends(monthlyTrends)
                .build();
    }

    private Map<String, SummarizeFinancialRecordsResponse.MonthlyTrend> buildMonthlyTrends(
            List<FinancialRecord> records,
            LocalDateTime trendStart,
            LocalDateTime trendEnd) {


        Map<String, List<FinancialRecord>> groupedByMonth = records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getTransactionTime().format(MONTH_FORMATTER),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));


        Map<String, SummarizeFinancialRecordsResponse.MonthlyTrend> monthlyTrends = new LinkedHashMap<>();

        YearMonth current = YearMonth.from(trendStart);
        YearMonth endMonth = YearMonth.from(trendEnd);

        while (!current.isAfter(endMonth)) {
            String monthKey = current.format(MONTH_FORMATTER);
            List<FinancialRecord> monthRecords = groupedByMonth.getOrDefault(monthKey, List.of());

            BigDecimal income = monthRecords.stream()
                    .filter(r -> r.getType() == RecordType.INCOME)
                    .map(FinancialRecord::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal expenses = monthRecords.stream()
                    .filter(r -> r.getType() == RecordType.EXPENSE)
                    .map(FinancialRecord::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            monthlyTrends.put(monthKey, SummarizeFinancialRecordsResponse.MonthlyTrend.builder()
                    .income(income)
                    .expenses(expenses)
                    .net(income.subtract(expenses))
                    .build());

            current = current.plusMonths(1);
        }

        return monthlyTrends;
    }
}