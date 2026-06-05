package com.quickcart.userservice.service.impl;

import com.quickcart.userservice.dto.LoginRequestDto;
import com.quickcart.userservice.dto.LoginResponseDto;
import com.quickcart.userservice.dto.UserCreatedRequestDto;
import com.quickcart.userservice.dto.UserResponseDto;
import com.quickcart.userservice.entity.User;
import com.quickcart.userservice.exception.ResourceAlreadyExistsException;
import com.quickcart.userservice.exception.ResourceNotFoundException;
import com.quickcart.userservice.mapper.AuthMapper;
import com.quickcart.userservice.mapper.UserMapper;
import com.quickcart.userservice.repository.UserRepository;
import com.quickcart.userservice.service.UserService;
import com.quickcart.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthMapper authMapper;

    @Override
    public UserResponseDto register(UserCreatedRequestDto userCreatedRequestDto) {
        if(userRepo.existsByEmail(userCreatedRequestDto.getEmail())){
            throw new ResourceAlreadyExistsException("Email already registered");
        }
        User user=userMapper.toEntity(userCreatedRequestDto);
        User savedUser=userRepo.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepo.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->new ResourceNotFoundException("Invalid Credential"));
        boolean matches= passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if(!matches){
            throw new ResourceNotFoundException("Invalid Credentials");
        }
        String  token=jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRoles());
        return authMapper.toLoginResponse(user,token);
    }

    @Override
    public UserResponseDto getById(String id) {
        User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found for this id"+id));
        return userMapper.toDto(user);
    }
}
