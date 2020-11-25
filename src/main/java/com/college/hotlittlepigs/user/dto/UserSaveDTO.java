package com.college.hotlittlepigs.user.dto;

import com.college.hotlittlepigs.user.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserSaveDTO {

  @NotBlank(message = "Name is required")
  private String name;

  @Email(message = "Invalid email address")
  private String email;

  @Size(min = 8, max = 16, message = "Password size must be between 8 and 16")
  private String password;

  public User toUser() {
    return new User(null, this.name, this.email, this.password, null, null);
  }
}
