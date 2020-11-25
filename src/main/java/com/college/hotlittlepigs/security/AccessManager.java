package com.college.hotlittlepigs.security;

import com.college.hotlittlepigs.user.User;
import com.college.hotlittlepigs.user.UserService;
import com.college.hotlittlepigs.user.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("accessManager")
@AllArgsConstructor
public class AccessManager {

  private final UserService userService;

  public boolean isOwner(Long id) {
    String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User user = userService.getByEmail(email);
    return user.getId().equals(id);
  }

  public boolean isAdmin() {
    String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userService.getByEmail(email);

    return user.getRole() == Role.ADMIN;
  }
}
