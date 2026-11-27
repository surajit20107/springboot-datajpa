package com.springDataJpa.service;

import com.springDataJpa.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springDataJpa.entities.Role;
import com.springDataJpa.entities.User;

import dto.CreateUserDto;
import dto.RegisterUserResponseDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserResponseDto registerUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setRole(Role.USER);

        User createdUser = userRepository.save(user);
        return new RegisterUserResponseDto(createdUser.getId(), createdUser.getName());
    }

}
