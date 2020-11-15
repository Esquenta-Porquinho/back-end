package com.college.hotlittlepigs.box.exception;

import com.college.hotlittlepigs.exception.NotFoundException;

public class BoxNotFoundException extends NotFoundException {
  public BoxNotFoundException() {
    super("Box not found");
  }
}
