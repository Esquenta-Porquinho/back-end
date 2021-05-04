package com.college.hotlittlepigs.gestation.expcetion;

import com.college.hotlittlepigs.exception.response.NotFoundException;

public class GestationNotFound extends NotFoundException {
  public GestationNotFound() {
    super("Gestation not found");
  }
}
