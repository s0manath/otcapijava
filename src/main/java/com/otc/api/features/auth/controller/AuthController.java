package com.otc.api.features.auth.controller;

import com.otc.api.features.auth.model.request.LoginRequest;
import com.otc.api.features.auth.model.response.LoginResponse;
import com.otc.api.features.auth.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Auth")
public class AuthController {

    private final LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginService.validateLogin(request));
    }
}
