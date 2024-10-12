package com.tenmo.app.authentication;

import com.tenmo.app.authentication.dto.LoginDto;
import com.tenmo.app.authentication.service.AuthenticationService;
import com.tenmo.app.authentication.dto.AuthenticationResponseDto;
import com.tenmo.app.authentication.dto.RegisterDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody LoginDto loginDto){
        return ResponseEntity.ok(authenticationService.login(loginDto));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody RegisterDto registerDto){
        return new ResponseEntity(
                authenticationService.register(registerDto),
                HttpStatus.CREATED);
    }
}