package com.college.hotlittlepigs.user.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginDTO {
    
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
}