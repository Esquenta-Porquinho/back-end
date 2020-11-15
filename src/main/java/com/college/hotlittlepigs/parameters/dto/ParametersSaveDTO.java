package com.college.hotlittlepigs.parameters.dto;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.parameters.Parameters;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Data
public class ParametersSaveDTO {

  @Digits(fraction = 2, integer = 2, message = "A number is required !")
  @NotNull(message = "The maximum ambient temperature is required !!")
  private Double ambientMax;

  @Digits(fraction = 2, integer = 2, message = "A number is required !")
  @NotNull(message = "The minimum ambient temperature is required !!")
  private Double ambientMin;

  @Digits(fraction = 2, integer = 2, message = "A number is required !")
  @NotNull(message = "The maximum floor temperature is required !!")
  private Double floorMax;

  @Digits(fraction = 2, integer = 2, message = "A number is required !")
  @NotNull(message = "The minimum floor temperature is required !!")
  private Double floorMin;

  @Digits(fraction = 2, integer = 2, message = "A number is required !")
  @NotNull(message = "The maximum boiler temperature is required !!")
  private Double boilerMax;

  @Digits(fraction = 2, integer = 2, message = "A number is required !")
  @NotNull(message = "The minimum boiler temperature is required !!")
  private Double boilerMin;

  @Digits(fraction = 0, integer = 2, message = "A integer number is required !")
  @NotNull(message = "The maximum boiler temperature is required !!")
  private Double weeks;

  @NotNull(message = "A box that's parameters belongs is required !!")
  private Box box;

  public Parameters toParameters() {
    return new Parameters(
        null,
        this.ambientMax,
        this.ambientMin,
        this.floorMax,
        this.floorMin,
        this.boilerMax,
        this.boilerMin,
        this.weeks,
        true,
        this.box);
  }
}
