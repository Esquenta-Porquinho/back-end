package com.college.hotlittlepigs.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class UserLoginServiceDTO {
    
    private String email;
    private List<String> roles;
    
}