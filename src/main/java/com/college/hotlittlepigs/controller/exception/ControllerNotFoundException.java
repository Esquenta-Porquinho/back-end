package com.college.hotlittlepigs.controller.exception;

import com.college.hotlittlepigs.exception.response.NotFoundException;

public class ControllerNotFoundException extends NotFoundException {
  public ControllerNotFoundException() {
    super("Controller not found");
  }
}
