package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.controllers.Controller;
import com.college.hotlittlepigs.controllers.ControllersRepository;
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
  @Autowired private ControllersRepository controllersRepository;

  @Test
  public void saveTest() {
    var requestBox = boxRepository.findById(1L);
    var box = requestBox.get();

    var controller = new Controller(null, "Controlador Boiler", false, box, null);
    var createdController = controllersRepository.save(controller);
    assertThat(createdController.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllTest() {
    var prm = new PageRequestModel();
    var page = controllersRepository.findAll(prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void getByIdTest() {
    var result = controllersRepository.findById(1L);
    var controller = result.get();
    assertThat(controller.getName()).isEqualTo("Controlador Boiler");
  }

  @Test
  public void findAllByBoxIdTest() {
    var prm = new PageRequestModel();
    var page = controllersRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void findAllByStatusTest() {
    var prm = new PageRequestModel();
    var page = controllersRepository.findAllByStatus(true, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(0);
  }

  @Test
  public void findAllByWorkTest() {
    var prm = new PageRequestModel();
    var page = controllersRepository.findAllByStatus(false, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }
}
