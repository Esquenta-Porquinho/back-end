package com.college.hotlittlepigs.meansurement.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.college.hotlittlepigs.meansurement.Measurement;
import com.college.hotlittlepigs.sensors.Sensors;

import lombok.Data;

@Data
public class MeasurementSaveDTO {
    
    @NotNull(message = "The measurement is required !!")
    @Digits(fraction = 2, integer = 2, message = "A number is required !!")
    private Double measure;

    @NotNull(message = "The sensor that's measure belongs is required !!")
    private Sensors sensor;

    public Measurement toMeasurement(){
        return new Measurement(null, this.measure, this.sensor);
    }

}
