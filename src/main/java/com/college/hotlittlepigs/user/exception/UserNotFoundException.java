package com.college.hotlittlepigs.user.exception;

import com.college.hotlittlepigs.exception.common.NotFoundException;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException() {
    super("User not found");
  }
}
