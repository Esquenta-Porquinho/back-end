package com.college.hotlittlepigs.parameters.expcetion;

import com.college.hotlittlepigs.exception.response.NotFoundException;

public class ParametersNotFoundException extends NotFoundException {
  public ParametersNotFoundException() {
    super("Parameters not found");
  }
}
