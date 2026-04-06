package com.zorvyn.finance.frontend.auth;

import com.zorvyn.finance.backend.data.constants.UserStatus;
import com.zorvyn.finance.backend.service.model.request.LookupUsersRequest;
import com.zorvyn.finance.backend.service.model.common.User;
import com.zorvyn.finance.backend.service.FinanceService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    private final FinanceService financeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)
                    && tokenProvider.validateToken(jwt)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                String email = tokenProvider.getEmailFromToken(jwt);

                User user = financeService.lookupUser(
                                LookupUsersRequest.builder().emailId(email).build()
                        ).getUsers()
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        user.getStatus() == UserStatus.ACTIVE,
                        true,
                        true,
                        true,
                        Collections.singleton(
                                new SimpleGrantedAuthority("ROLE_" + user.getRoleType().name()) // ✅ FIX
                        )
                );


                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                System.out.println("Loaded Authorities: " + userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("SecurityContext Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }
        } catch (Exception ex) {
            log.error("JWT authentication failed: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

