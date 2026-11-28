package com.springDataJpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springDataJpa.dto.CreateUserDto;
import com.springDataJpa.dto.LoginDto;
import com.springDataJpa.dto.UserLoginResponseDto;
import com.springDataJpa.dto.UserRegisterResponseDto;
import com.springDataJpa.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(createUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.loginUser(loginDto));
    }

}
