package com.college.hotlittlepigs.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import com.college.hotlittlepigs.controllers.Controllers;
import com.college.hotlittlepigs.controllers.ControllersRepository;
import com.college.hotlittlepigs.log_controllers.LogControllers;
import com.college.hotlittlepigs.log_controllers.LogControllersRepository;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.DEFAULT)
@SpringBootTest
public class LogControllersRepositoryTests {
    
    @Autowired private LogControllersRepository logControllersRepository;
    @Autowired private ControllersRepository controllersRepository;

    @Test
    public void saveTest(){
        Optional<Controllers> request = controllersRepository.findById(1L);
        Controllers controller = request.get();
        LogControllers log = new LogControllers(null, new Date(), true, controller);
        LogControllers createdLog = logControllersRepository.save(log);

        assertThat(createdLog.getId()).isEqualTo(1L);    
    }

    @Test
    public void findAllByControllerIdTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<LogControllers> page = logControllersRepository.findAllByControllerId(1L, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

}