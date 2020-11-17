package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.controllers.ControllersRepository;
import com.college.hotlittlepigs.log_controllers.LogController;
import com.college.hotlittlepigs.log_controllers.LogControllersRepository;
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
public class LogControllerRepositoryTests {

  @Autowired private LogControllersRepository logControllersRepository;
  @Autowired private ControllersRepository controllersRepository;

  @Test
  public void saveTest() {
    var request = controllersRepository.findById(1L);
    var controller = request.get();
    var log = new LogController(null, new Date(), true, controller);
    var createdLog = logControllersRepository.save(log);

    assertThat(createdLog.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllByControllerIdTest() {
    var prm = new PageRequestModel();
    var page = logControllersRepository.findAllByControllerId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }
}
