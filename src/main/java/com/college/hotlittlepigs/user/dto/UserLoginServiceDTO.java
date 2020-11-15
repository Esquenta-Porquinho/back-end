package com.college.hotlittlepigs.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginServiceDTO {

  private String email;
  private List<String> roles;
}
