package com.fulbo.infrastructure.adapter.in.rest;

import com.fulbo.application.dto.request.LoginRequest;
import com.fulbo.application.dto.request.RegisterRequest;
import com.fulbo.application.dto.response.AuthResponse;
import com.fulbo.application.dto.response.UserResponse;
import com.fulbo.domain.model.User;
import com.fulbo.domain.port.in.AuthUseCase;
import com.fulbo.infrastructure.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthUseCase authUseCase, JwtTokenProvider jwtTokenProvider) {
        this.authUseCase = authUseCase;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = authUseCase.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getDisplayName()
        );

        String token = authUseCase.login(request.getEmail(), request.getPassword());
        String refreshToken = jwtTokenProvider.generateRefreshToken(request.getEmail());

        AuthResponse response = new AuthResponse(token, refreshToken, UserResponse.fromDomain(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authUseCase.login(request.getEmail(), request.getPassword());
        User user = authUseCase.getCurrentUser(token);
        String refreshToken = jwtTokenProvider.generateRefreshToken(request.getEmail());

        return ResponseEntity.ok(new AuthResponse(token, refreshToken, UserResponse.fromDomain(user)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.substring(7);
        String newToken = authUseCase.refreshToken(token);
        User user = authUseCase.getCurrentUser(newToken);
        String email = user.getEmail();
        String refreshToken = jwtTokenProvider.generateRefreshToken(email);

        return ResponseEntity.ok(new AuthResponse(newToken, refreshToken, UserResponse.fromDomain(user)));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.substring(7);
        User user = authUseCase.getCurrentUser(token);
        return ResponseEntity.ok(UserResponse.fromDomain(user));
    }
}
