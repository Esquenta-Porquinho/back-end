package com.college.hotlittlepigs.user.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class UserLoginDTO {
    
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
}