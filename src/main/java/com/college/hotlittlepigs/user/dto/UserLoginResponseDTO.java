package com.college.hotlittlepigs.user.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class UserLoginResponseDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    

    private String token;
    private Long expireIn;
    private String tokenProvider;

}