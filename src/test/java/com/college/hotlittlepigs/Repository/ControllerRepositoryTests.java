package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.controller.Controller;
import com.college.hotlittlepigs.controller.ControllerRepository;
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
public class ControllerRepositoryTests {

  @Autowired private BoxRepository boxRepository;
  @Autowired private ControllerRepository controllerRepository;

  @Test
  public void saveTest() {
    var requestBox = boxRepository.findById(1L);
    var box = requestBox.get();

    var controller = new Controller(null, "Controlador Boiler", false, box, null);
    var createdController = controllerRepository.save(controller);
    assertThat(createdController.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllTest() {
    var prm = new PageRequestModel();
    var page = controllerRepository.findAll(prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void getByIdTest() {
    var result = controllerRepository.findById(1L);
    var controller = result.get();
    assertThat(controller.getName()).isEqualTo("Controlador Boiler");
  }

  @Test
  public void findAllByBoxIdTest() {
    var prm = new PageRequestModel();
    var page = controllerRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void findAllByStatusTest() {
    var prm = new PageRequestModel();
    var page = controllerRepository.findAllByStatus(true, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(0);
  }

  @Test
  public void findAllByWorkTest() {
    var prm = new PageRequestModel();
    var page = controllerRepository.findAllByStatus(false, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }
}
