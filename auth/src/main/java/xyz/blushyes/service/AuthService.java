package xyz.blushyes.service;

import xyz.blushyes.request.RegisterRequest;

public interface AuthService {
    /**
     * @return JWT token
     */
    String login(String username, String password);

    void register(RegisterRequest request);

    boolean checkState();
}
