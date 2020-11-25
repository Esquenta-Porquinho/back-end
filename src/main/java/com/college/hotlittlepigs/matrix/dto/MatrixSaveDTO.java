package com.college.hotlittlepigs.matrix.dto;

import com.college.hotlittlepigs.matrix.Matrix;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MatrixSaveDTO {

  @NotNull(message = "Number is required")
  private Integer number;

  public Matrix toMatrix() {
    return new Matrix(null, this.number, true, null);
  }
}
