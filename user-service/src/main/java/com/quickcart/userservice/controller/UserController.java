package com.quickcart.userservice.controller;

import com.quickcart.userservice.dto.LoginRequestDto;
import com.quickcart.userservice.dto.LoginResponseDto;
import com.quickcart.userservice.dto.UserCreatedRequestDto;
import com.quickcart.userservice.dto.UserResponseDto;
import com.quickcart.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreatedRequestDto dto){
        UserResponseDto userResponseDto= userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginResponseDto=userService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable String id, Authentication authentication){
        UserResponseDto userResponseDto=userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }
}
