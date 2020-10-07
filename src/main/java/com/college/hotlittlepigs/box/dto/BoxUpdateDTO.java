package com.college.hotlittlepigs.box.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoxUpdateDTO{
    
    @NotBlank(message = "The controller of the Box is required !!")
    private String controller;

    @NotBlank(message = "The area of the Box is required !!")
    private String area;
}
