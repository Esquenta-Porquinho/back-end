package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.parameters.Parameters;
import com.college.hotlittlepigs.parameters.ParametersRepository;
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
public class ParametersRepositoryTests {

  @Autowired private BoxRepository boxRepository;
  @Autowired private ParametersRepository parametersRepository;

  @Test
  public void saveTest() {
    var requestBox = boxRepository.findById(1L);
    var box = requestBox.get();

    var parameters =
        new Parameters(null, 28.00, 23.00, 28.00, 23.00, 28.00, 23.00, 1.0, false, box);
    var createdParameters = parametersRepository.save(parameters);
    assertThat(createdParameters.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllTest() {
    var prm = new PageRequestModel();
    var page = parametersRepository.findAll(prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void getByIdTest() {
    var result = parametersRepository.findById(1L);
    var parameters = result.get();
    assertThat(parameters.getId()).isEqualTo(1);
  }

  @Test
  public void findAllByBoxIdTest() {
    var prm = new PageRequestModel();
    var page = parametersRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void findActiveByWeekByBoxTest() {
    var result = parametersRepository.findByBoxIdAndWeeksAndStatusIsTrue(1L, 1.0);
    var parameters = result.get();
    assertThat(parameters.getId()).isEqualTo(1);
  }
}
