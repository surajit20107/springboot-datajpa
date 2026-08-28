package com.springDataJpa.service;

import org.springframework.stereotype.Service;

import com.springDataJpa.entities.User;
import com.springDataJpa.repository.UserRepository;

import dto.CreateUserDto;
import dto.UserDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());

        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

}
