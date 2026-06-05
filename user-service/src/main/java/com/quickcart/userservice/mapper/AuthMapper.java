package com.quickcart.userservice.mapper;

import com.quickcart.userservice.dto.LoginResponseDto;
import com.quickcart.userservice.entity.User;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthMapper {
    public LoginResponseDto toLoginResponse(User user,String token){
        return new LoginResponseDto(token,"Bearer", user.getId(), user.getEmail(), user.getName());
    }
}
