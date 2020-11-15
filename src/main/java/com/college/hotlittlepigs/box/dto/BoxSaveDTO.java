package com.college.hotlittlepigs.box.dto;

import com.college.hotlittlepigs.box.Box;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BoxSaveDTO {

  @NotNull(message = "Number of the Box is required")
  private Integer number;

  @NotBlank(message = "The controller of the Box is required")
  private String controller;

  @NotBlank(message = "The area of the Box is required")
  private String area;

  public Box toBox() {
    return new Box(null, this.number, this.controller, this.area, true, null, null, null, null);
  }
}
