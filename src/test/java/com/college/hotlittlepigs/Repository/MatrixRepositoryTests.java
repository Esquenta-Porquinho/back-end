package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.matrix.Matrix;
import com.college.hotlittlepigs.matrix.MatrixRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder
@SpringBootTest
public class MatrixRepositoryTests {

  @Autowired private MatrixRepository matrixRepository;

  @Test
  public void saveTest() {
    var newMatrix = new Matrix(null, 39, true, null);
    var createdMatrix = matrixRepository.save(newMatrix);

    assertThat(createdMatrix.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllTest() {
    var prm = new PageRequestModel();
    var page = matrixRepository.findAll(prm.toSpringPageRequest());

    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void getByIdTest() {
    var result = matrixRepository.findById(1L);
    var matrix = result.get();

    assertThat(matrix.getNumber()).isEqualTo(39);
  }
}
