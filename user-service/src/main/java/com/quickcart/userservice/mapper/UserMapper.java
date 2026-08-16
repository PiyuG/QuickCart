package com.quickcart.userservice.mapper;

import com.quickcart.userservice.dto.UserCreatedRequestDto;
import com.quickcart.userservice.dto.UserResponseDto;
import com.quickcart.userservice.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public UserMapper(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    public User toEntity(UserCreatedRequestDto dto){
        return User.builder().name(dto.getName()).email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword())).mobileNo(dto.getMobileNo())
                .roles( dto.getRole()==null ? "ROLE_USER": "ROLE_ADMIN").build();
    }
    public UserResponseDto toDto(User user){
        return UserResponseDto.builder().id(user.getId())
                .name(user.getName()).email((user.getEmail()))
                .mobileNo(user.getMobileNo()).roles(user.getRoles())
                .createdAt(user.getCreatedAt()).updatedAt(user.getUpdatedAt())
                .build();
    }
}
