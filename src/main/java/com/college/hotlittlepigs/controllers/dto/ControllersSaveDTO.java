package com.college.hotlittlepigs.controllers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.controllers.Controllers;

import lombok.Data;

@Data
public class ControllersSaveDTO {
    
    @NotEmpty(message = "The name of the controller is required !!")
    private String name;
    
    @NotNull(message = "The box that's controller belongs is required !!")
    private Box box;

    public Controllers toController(){
        return new Controllers(null, this.name, false, this.box, null);
    }

}
