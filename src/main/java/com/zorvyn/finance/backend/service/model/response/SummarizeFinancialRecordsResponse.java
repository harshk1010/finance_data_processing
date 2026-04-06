package com.zorvyn.finance.backend.service.model.response;

import com.zorvyn.finance.backend.data.constants.RecordCategory;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class SummarizeFinancialRecordsResponse extends BaseResponse {

    private BigDecimal totalIncome;

    private BigDecimal totalExpenses;

    private BigDecimal netBalance;

    private Map<RecordCategory, BigDecimal> categoryTotals;


    private List<RecentActivity> recentActivity;

    private Map<String, MonthlyTrend> monthlyTrends;

    @Builder
    @Getter
    public static class RecentActivity {
        private Long id;
        private BigDecimal amount;
        private String type;
        private String category;
        private String transactionTime;
        private String description;
    }

    @Builder
    @Getter
    public static class MonthlyTrend {
        private BigDecimal income;
        private BigDecimal expenses;
        private BigDecimal net;
    }
}