package com.college.hotlittlepigs.matrix.dto;

import javax.validation.constraints.NotNull;

import com.college.hotlittlepigs.matrix.Matrix;

import lombok.Data;

@Data
public class MatrixSaveDTO {
    
    @NotNull(message="Number is required")
    private Integer number;


    public Matrix toMatrix(){
        Matrix matrix = new Matrix(null, this.number, true, null);
        return matrix;
    }
}
