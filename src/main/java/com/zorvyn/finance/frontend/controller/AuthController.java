package com.zorvyn.finance.frontend.controller;

import com.zorvyn.finance.backend.data.constants.UserStatus;
import com.zorvyn.finance.backend.service.FinanceService;
import com.zorvyn.finance.frontend.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final FinanceService financeService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        var userOpt = financeService.findUserByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid credentials"));
        }

        var user = userOpt.get();

        if (user.getStatus() == UserStatus.INACTIVE) {
            return ResponseEntity.status(403)
                    .body(Map.of("error", "Account is inactive"));
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid credentials"));
        }

        String token = tokenProvider.generateToken(email);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "expiresIn", tokenProvider.getExpirationMs(),
                "role", user.getRoleType().name()
        ));
    }
}