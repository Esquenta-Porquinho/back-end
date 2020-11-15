package com.college.hotlittlepigs.box.exception;

import com.college.hotlittlepigs.exception.common.NotFoundException;

public class ParametersNotFoundException extends NotFoundException {
  public ParametersNotFoundException() {
    super("Parameters not found");
  }
}
