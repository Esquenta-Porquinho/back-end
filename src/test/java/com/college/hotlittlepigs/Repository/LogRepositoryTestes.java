package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.log.Log;
import com.college.hotlittlepigs.log.LogRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.user.UserRepository;
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
public class LogRepositoryTestes {

  @Autowired private LogRepository logRepository;
  @Autowired private UserRepository userRepository;

  @Test
  public void saveTest() {
    var request = userRepository.findById(1L);
    var user = request.get();
    var log = new Log(null, "Teste de Inserção", new Date(), user);
    var createdLog = logRepository.save(log);

    assertThat(createdLog.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllByOwnerId() {
    var prm = new PageRequestModel();
    var page = logRepository.findAllByOwnerId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }
}
