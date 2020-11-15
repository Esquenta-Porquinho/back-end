package com.college.hotlittlepigs.controllers.exception;

import com.college.hotlittlepigs.exception.common.NotFoundException;

public class ControllerNotFoundException extends NotFoundException {
  public ControllerNotFoundException() {
    super("Controller not found");
  }
}
