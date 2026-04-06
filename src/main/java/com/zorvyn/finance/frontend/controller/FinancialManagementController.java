package com.zorvyn.finance.frontend.controller;

import com.zorvyn.finance.backend.service.model.request.CreateFinancialRecordRequest;
import com.zorvyn.finance.backend.service.model.request.UpdateFinancialRecordRequest;
import com.zorvyn.finance.backend.service.model.request.DeleteFinancialRecordRequest;
import com.zorvyn.finance.backend.service.model.request.LookupFinancialRecordsRequest;

import com.zorvyn.finance.backend.service.model.response.CreateFinancialRecordResponse;
import com.zorvyn.finance.backend.service.model.response.UpdateFinancialRecordResponse;
import com.zorvyn.finance.backend.service.model.response.LookupFinancialRecordsResponse;

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
@RequestMapping("/api/finance")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class FinancialManagementController {

    private final FinanceService financeService;

    @PostMapping("/create")
    @Operation(summary = "Create Financial Record")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateFinancialRecordResponse> createRecord(
            @Valid @RequestBody CreateFinancialRecordRequest request,
            @CurrentUser UserDetails currentUser) {

        CreateFinancialRecordResponse response =
                financeService.createFinancialRecord(
                        request,
                        currentUser.getUsername()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Financial Records")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public ResponseEntity<LookupFinancialRecordsResponse> getRecords(
            @ModelAttribute @Valid LookupFinancialRecordsRequest request,
            @CurrentUser UserDetails currentUser) {

        LookupFinancialRecordsResponse response =
                financeService.lookupFinancialRecords(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    @Operation(summary = "Update Financial Record")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateFinancialRecordResponse> updateRecord(
            @Valid @RequestBody UpdateFinancialRecordRequest request,
            @CurrentUser UserDetails currentUser) {

        UpdateFinancialRecordResponse response =
                financeService.updateFinancialRecord(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    @Operation(summary = "Delete Financial Record")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRecord(
            @Valid @RequestBody DeleteFinancialRecordRequest request,
            @CurrentUser UserDetails currentUser) {

        financeService.deleteFinancialRecord(request);
        return ResponseEntity.noContent().build();
    }

}