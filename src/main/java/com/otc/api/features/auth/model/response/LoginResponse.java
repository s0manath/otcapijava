package com.otc.api.features.auth.model.response;


public class LoginResponse {
    private boolean success;
    private String message = "";
    private String token = "";
    private String username = "";

    public LoginResponse() {}
    public LoginResponse(boolean success, String message, String token, String username) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.username = username;
    }
    public boolean isSuccess() { return this.success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return this.message; }
    public void setMessage(String message) { this.message = message; }
    public String getToken() { return this.token; }
    public void setToken(String token) { this.token = token; }
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
}
