package com.college.hotlittlepigs.log;

import com.college.hotlittlepigs.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("logHandler")
public class LogHandler {

  private final LogService logService;
  private final UserService userService;

  public Boolean saveLog(String action) {
    var email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var user = userService.getByEmail(email);

    // TODO por que você retorna true e false?
    try {
      System.out.println(action);
      logService.save(action, user);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
