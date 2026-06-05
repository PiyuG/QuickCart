package com.quickcart.userservice.service;

import com.quickcart.userservice.dto.LoginRequestDto;
import com.quickcart.userservice.dto.LoginResponseDto;
import com.quickcart.userservice.dto.UserCreatedRequestDto;
import com.quickcart.userservice.dto.UserResponseDto;
import com.quickcart.userservice.entity.User;

import java.util.Optional;

public interface UserService {

    UserResponseDto register(UserCreatedRequestDto userCreatedRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    UserResponseDto getById(String id);
}
