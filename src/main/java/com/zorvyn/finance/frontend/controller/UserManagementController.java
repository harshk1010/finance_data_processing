package com.zorvyn.finance.frontend.controller;

import com.zorvyn.finance.backend.service.model.request.CreateUserRequest;
import com.zorvyn.finance.backend.service.model.request.UpdateUserRequest;
import com.zorvyn.finance.backend.service.model.request.DeleteUserRequest;
import com.zorvyn.finance.backend.service.model.request.LookupUsersRequest;

import com.zorvyn.finance.backend.service.model.response.CreateUserResponse;
import com.zorvyn.finance.backend.service.model.response.UpdateUserResponse;
import com.zorvyn.finance.backend.service.model.response.LookupUsersResponse;

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
@RequestMapping("/api/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class UserManagementController {

    private final FinanceService financeService;

    @PostMapping("/create")
    @Operation(summary = "Create User")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request,
            @CurrentUser UserDetails currentUser) {

        CreateUserResponse response = financeService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LookupUsersResponse> getUsers(
            @ModelAttribute @Valid LookupUsersRequest request,
            @CurrentUser UserDetails currentUser) {

        LookupUsersResponse response = financeService.lookupUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    @Operation(summary = "Update User")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @Valid @RequestBody UpdateUserRequest request,
            @CurrentUser UserDetails currentUser) {

        UpdateUserResponse response = financeService.updateUser(request);
        System.out.println("Authorities: " + currentUser.getAuthorities());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    @Operation(summary = "Delete User")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @Valid @RequestBody DeleteUserRequest request,
            @CurrentUser UserDetails currentUser) {

        financeService.deleteUser(request);
        return ResponseEntity.noContent().build();
    }
}