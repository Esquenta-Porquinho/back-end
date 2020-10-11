package com.college.hotlittlepigs.meansurement.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MeasurementListSaveDTO {
    
    @NotNull(message = "The list of measurement is required !!")
    private List< @Valid MeasurementSaveDTO> list;

}
