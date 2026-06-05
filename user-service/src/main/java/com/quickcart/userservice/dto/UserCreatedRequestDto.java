package com.quickcart.userservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreatedRequestDto {
    @NotBlank(message = "Name Required")
    private String name;
    @Email(message = "Valid email Required")
    @NotBlank(message = "Email Required")
    private String email;
    @NotBlank(message = "Password Required")
    @Size(min = 6,message = "password must be at least 6 characters")
    private String password;
    private String mobileNo;
}
