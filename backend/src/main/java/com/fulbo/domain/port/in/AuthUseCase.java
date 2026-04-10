package com.fulbo.domain.port.in;

import com.fulbo.domain.model.User;

public interface AuthUseCase {
    User register(String username, String email, String password, String displayName);
    String login(String email, String password);
    String refreshToken(String token);
    User getCurrentUser(String token);
}
