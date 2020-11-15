package com.college.hotlittlepigs.exception.common;

public class JwtException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public JwtException(String msg) {
    super(msg);
  }
}
