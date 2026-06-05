package com.quickcart.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String id;
    private String name;
    private String email;
    private String roles;
    private String mobileNo;
    private Instant createdAt;
    private Instant updatedAt;
}
