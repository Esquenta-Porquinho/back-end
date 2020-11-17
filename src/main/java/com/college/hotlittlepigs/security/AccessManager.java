package com.college.hotlittlepigs.security;

import com.college.hotlittlepigs.user.UserService;
import com.college.hotlittlepigs.user.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("accessManager")
public class AccessManager {

  private final UserService userService;

  public AccessManager(@Lazy UserService userService) {
    this.userService = userService;
  }

  public boolean isOwner(Long id) {
    var email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    var user = userService.getByEmail(email);
    return user.getId().equals(id);
  }

  public boolean isAdmin() {
    var email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var user = userService.getByEmail(email);

    return user.getRole() == Role.ADMIN;
  }
}
