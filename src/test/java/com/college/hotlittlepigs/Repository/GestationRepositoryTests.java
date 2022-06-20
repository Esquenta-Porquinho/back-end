package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.gestation.Gestation;
import com.college.hotlittlepigs.gestation.GestationRepository;
import com.college.hotlittlepigs.matrix.MatrixRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder
@SpringBootTest
public class GestationRepositoryTests {

  @Autowired private BoxRepository boxRepository;
  @Autowired private MatrixRepository matrixRepository;
  @Autowired private GestationRepository gestationRepository;

  @Test
  public void saveTest() {
    var requestBox = boxRepository.findById(1L);
    var box = requestBox.get();

    var requestMatrix = matrixRepository.findById(1L);
    var matrix = requestMatrix.get();
    var gestation = new Gestation(null, null, null, new Date(), null, 4, null, true, box, matrix);
    var createdGestation = gestationRepository.save(gestation);
    assertThat(createdGestation.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllTest() {
    var prm = new PageRequestModel();
    var page = gestationRepository.findAll(prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void getByIdTest() {
    var result = gestationRepository.findById(1L);
    var gestation = result.get();
    assertThat(gestation.getId()).isEqualTo(1);
  }

  @Test
  public void findAllByBoxIdTest() {
    var prm = new PageRequestModel();
    var page = gestationRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void findAllByMatrixIdTest() {
    var prm = new PageRequestModel();
    var page = gestationRepository.findAllByMatrixId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }
}
