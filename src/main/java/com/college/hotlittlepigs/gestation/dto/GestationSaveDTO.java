package com.college.hotlittlepigs.gestation.dto;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.gestation.Gestation;
import com.college.hotlittlepigs.matrix.Matrix;

import lombok.Data;

@Data
public class GestationSaveDTO {
    
    @Digits(fraction = 0 ,integer = 2, message = "A integer number is required !")
    private Integer livePigs;

    @Digits(fraction = 0 ,integer = 2, message = "A integer number is required !")
    private Integer deadPigs;

    @NotNull(message = "Expected date of parturition is required !!")
    private Date expectedParturition;

    private Date effectiveParturition;

    @Digits(fraction = 0 ,integer = 2, message = "A integer number is required !")
    @NotNull(message = "Number of parturition is required !!")
    private Integer numberParturition;

    private Date weaning;

    @NotNull(message = "The Box that's gestation belongs is required !!")
    private Box box;

    @NotNull(message = "The Matrix that's gestation belongs is required !!")
    private Matrix matrix;

    public Gestation toGestation() {
        return new Gestation(
            null, 
            this.livePigs, 
            this.deadPigs, 
            this.expectedParturition, 
            this.effectiveParturition, 
            this.numberParturition, 
            this.weaning,
            false,
            this.box,
            this.matrix
        );
    }
}
