package com.college.hotlittlepigs.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginServiceDTO {
    
    private String email;
    private List<String> roles;
    
}