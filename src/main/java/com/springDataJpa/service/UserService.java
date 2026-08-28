package com.springDataJpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springDataJpa.entities.User;
import com.springDataJpa.repository.UserRepository;

import dto.CreateUserDto;
import dto.UpdateUserDto;
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

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        return userDto;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    // public UserDto patchUser(Long id, UpdateUserDto patchUserDto) { return null;}

    // public UserDto updateUser(Long id, UpdateUserDto updateUserDto) { return null;}

}
