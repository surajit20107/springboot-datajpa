package com.springDataJpa.service;

import com.springDataJpa.repository.UserRepository;
import com.springDataJpa.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springDataJpa.entities.Role;
import com.springDataJpa.entities.User;
import com.springDataJpa.exception.UserNotFoundException;

import dto.CreateUserDto;
import dto.LoginDto;
import dto.UserLoginResponseDto;
import dto.UserRegisterResponseDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserRegisterResponseDto registerUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setRole(Role.USER);

        User createdUser = userRepository.save(user);
        return new UserRegisterResponseDto(createdUser.getId(), createdUser.getName());
    }

    public UserLoginResponseDto loginUser(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            String jwtToken = jwtService.generateJwtToken((UserDetails) authentication.getPrincipal());
            return new UserLoginResponseDto(jwtToken);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
