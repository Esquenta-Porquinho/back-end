package com.college.hotlittlepigs.matrix.exception;

import com.college.hotlittlepigs.exception.response.NotFoundException;

public class MatrixNotFoundException extends NotFoundException {
  public MatrixNotFoundException() {
    super("Matrix not found");
  }
}
