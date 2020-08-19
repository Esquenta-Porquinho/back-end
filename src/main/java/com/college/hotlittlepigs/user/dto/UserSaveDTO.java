package com.college.hotlittlepigs.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.college.hotlittlepigs.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSaveDTO {
    
    @NotBlank(message="Name is required")
    private String name;
    
    @Email(message="Invalid email address")
    private String email;
    
    @Size(min=7, max=99, message="Password size must be between 7 and 99")
    private String password;
    
    public User transformToUser(){
        User user = new User(null, this.name, this.email, this.password, null);
        return user;
    }
}