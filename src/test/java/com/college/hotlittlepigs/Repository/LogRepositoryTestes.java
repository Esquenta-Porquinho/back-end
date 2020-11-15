package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.log.Log;
import com.college.hotlittlepigs.log.LogRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.user.User;
import com.college.hotlittlepigs.user.UserRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.DEFAULT)
@SpringBootTest
public class LogRepositoryTestes {

  @Autowired private LogRepository logRepository;
  @Autowired private UserRepository userRepository;

  @Test
  public void saveTest() {
    Optional<User> request = userRepository.findById(1L);
    User user = request.get();
    Log log = new Log(null, "Teste de Inserção", new Date(), user);
    Log createdLog = logRepository.save(log);

    assertThat(createdLog.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllByOwnerId() {
    PageRequestModel prm = new PageRequestModel();
    Page<Log> page = logRepository.findAllByOwnerId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }
}
