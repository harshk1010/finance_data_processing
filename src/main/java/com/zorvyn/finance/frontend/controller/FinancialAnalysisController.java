package com.zorvyn.finance.frontend.controller;

import com.zorvyn.finance.backend.service.model.request.SummarizeFinancialRecordsRequest;
import com.zorvyn.finance.backend.service.model.response.SummarizeFinancialRecordsResponse;
import com.zorvyn.finance.frontend.auth.CurrentUser;
import com.zorvyn.finance.backend.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class FinancialAnalysisController {

    private final FinanceService financeService;

    @GetMapping
    @Operation(summary = "Financial Summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    public ResponseEntity<SummarizeFinancialRecordsResponse> getFinancialSummary(
            @ModelAttribute @Valid SummarizeFinancialRecordsRequest request,
            @CurrentUser UserDetails currentUser) {

        SummarizeFinancialRecordsResponse response =
                financeService.summarizeFinancialRecords(request);

        return ResponseEntity.ok(response);
    }
}