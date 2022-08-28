package com.queueup.qup.service.impl;

import com.queueup.qup.PasswordEncryption;
import com.queueup.qup.dto.UserDto;
import com.queueup.qup.entity.User;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;
    private final PasswordEncryption passwordEncryption;

    public UserServiceImpl(UserRepo userRepo,
                           PasswordEncryption passwordEncryption) {

        this.userRepo = userRepo;
        this.passwordEncryption = passwordEncryption;
    }

    @Override
    public UserDto save(UserDto userDto) {
        User entity = User.builder()
                .id(userDto.getId())
                .userName(userDto.getUserName())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncryption.getEncryptedPassword(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .gender(userDto.getGender())
                .build();
        entity = userRepo.save(entity);
        return userDto.builder()
                .id(entity.getId())
                .userName(userDto.getUserName())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .gender(userDto.getGender())
                .build();
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepo.findAll();
        return userList.stream().map(
                user -> UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .gender(user.getGender())
                        .phoneNumber(user.getPhoneNumber())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {

        return null;
    }

    @Override
    public void deleteById(Integer id) {
        userRepo.deleteById(id);
    }

}
