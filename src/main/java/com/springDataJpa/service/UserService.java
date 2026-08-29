package com.springDataJpa.service;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springDataJpa.entities.User;
import com.springDataJpa.repository.UserRepository;

import dto.CreateUserDto;
import dto.UpdateUserDto;
import dto.UserDto;
import jakarta.transaction.Transactional;
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

    @Transactional
    public UserDto patchUser(Long id, UpdateUserDto patchUserDto) {
        User user = userRepository.findById(id).orElseThrow();
        if (patchUserDto.getName() != null) {
            user.setName(patchUserDto.getName());
        }

        if (patchUserDto.getEmail() != null) {
            user.setEmail(patchUserDto.getEmail());
        }

        UserDto userDto =  new UserDto(user.getId(), user.getName(), user.getEmail());
        return userDto;
    }

    @Transactional
    public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(updateUserDto.getName());
        user.setEmail(updateUserDto.getEmail());
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public List<UserDto> getUsersPaginated(int page, int size, String direction, String sortBy) {
        Sort sort;
        sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<User> users = userRepository.findAll(pageable);

        List<UserDto> userDtoList = new ArrayList<>();  
        users.forEach(user -> userDtoList.add(new UserDto(user.getId(), user.getName(), user.getEmail())));  
        
        return userDtoList;
    }

}
