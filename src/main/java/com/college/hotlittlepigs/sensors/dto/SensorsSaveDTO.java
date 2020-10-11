package com.college.hotlittlepigs.sensors.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.sensors.Sensors;

import lombok.Data;

@Data
public class SensorsSaveDTO {
    
    @NotBlank(message = "The sensor's data type is required !!")
    private String dataType;

    @NotBlank(message = "The sensor's name is required !!")
    private String name;

    @NotNull(message = "The box that's the sensor belongs is required !!")
    private Box box;

    public Sensors toSensors(){
        return new Sensors(null, this.dataType, this.name, false, this.box, null);
    }
}
