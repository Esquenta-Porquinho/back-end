package com.college.hotlittlepigs.sensors.exception;

import com.college.hotlittlepigs.exception.common.NotFoundException;

public class SensorNotFoundException extends NotFoundException {
  public SensorNotFoundException() {
    super("Sensor not found");
  }
}
