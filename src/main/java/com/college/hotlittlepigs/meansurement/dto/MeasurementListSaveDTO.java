package com.college.hotlittlepigs.meansurement.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MeasurementListSaveDTO {

  @NotNull(message = "The list of measurement is required !!")
  private List<@Valid MeasurementSaveDTO> list;
}
