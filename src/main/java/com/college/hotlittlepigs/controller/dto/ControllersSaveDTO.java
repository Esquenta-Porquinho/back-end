package com.college.hotlittlepigs.controller.dto;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.controller.Controller;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ControllersSaveDTO {

  @NotEmpty(message = "The name of the controller is required")
  private String name;

  @NotNull(message = "The box that's controller belongs is required")
  private Box box;

  public Controller toController() {
    return new Controller(null, this.name, false, this.box, null);
  }
}
