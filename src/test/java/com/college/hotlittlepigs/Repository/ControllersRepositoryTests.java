package com.college.hotlittlepigs.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.controllers.Controllers;
import com.college.hotlittlepigs.controllers.ControllersRepository;
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
public class ControllersRepositoryTests {
    
    @Autowired private BoxRepository boxRepository;
    @Autowired private ControllersRepository controllersRepository;

    @Test
    public void saveTest(){
        Optional<Box> requestBox = boxRepository.findById(1L);
        Box box = requestBox.get();
        
        Controllers controllers = new Controllers(null,"Controlador Boiler", false, false, box);
        Controllers createdControllers = controllersRepository.save(controllers);
        assertThat(createdControllers.getId()).isEqualTo(1L);    
    }

    @Test
    public void findAllTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Controllers> page = controllersRepository.findAll(prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void getByIdTest(){
        Optional<Controllers> result = controllersRepository.findById(1L);
        Controllers controllers = result.get();
        assertThat(controllers.getName()).isEqualTo("Controlador Boiler");
    }

    @Test
    public void findAllByBoxIdTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Controllers> page = controllersRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void findAllByStatusTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Controllers> page = controllersRepository.findAllByStatus(true, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(0);
    }

    @Test
    public void findAllByWorkTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Controllers> page = controllersRepository.findAllByStatus(false, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }
}
