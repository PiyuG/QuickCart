package com.quickcart.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {

    private String token;
    private String tokenType="Bearer";
    private String userId;
    private String email;
    private String name;
}
