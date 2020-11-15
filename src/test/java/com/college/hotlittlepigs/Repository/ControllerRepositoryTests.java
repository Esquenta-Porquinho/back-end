package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.controllers.Controller;
import com.college.hotlittlepigs.controllers.ControllersRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.DEFAULT)
@SpringBootTest
public class ControllerRepositoryTests {

  @Autowired private BoxRepository boxRepository;
  @Autowired private ControllersRepository controllersRepository;

  @Test
  public void saveTest() {
    Optional<Box> requestBox = boxRepository.findById(1L);
    Box box = requestBox.get();

    Controller controller = new Controller(null, "Controlador Boiler", false, box, null);
    Controller createdController = controllersRepository.save(controller);
    assertThat(createdController.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllTest() {
    PageRequestModel prm = new PageRequestModel();
    Page<Controller> page = controllersRepository.findAll(prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void getByIdTest() {
    Optional<Controller> result = controllersRepository.findById(1L);
    Controller controller = result.get();
    assertThat(controller.getName()).isEqualTo("Controlador Boiler");
  }

  @Test
  public void findAllByBoxIdTest() {
    PageRequestModel prm = new PageRequestModel();
    Page<Controller> page = controllersRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void findAllByStatusTest() {
    PageRequestModel prm = new PageRequestModel();
    Page<Controller> page = controllersRepository.findAllByStatus(true, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(0);
  }

  @Test
  public void findAllByWorkTest() {
    PageRequestModel prm = new PageRequestModel();
    Page<Controller> page = controllersRepository.findAllByStatus(false, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }
}
