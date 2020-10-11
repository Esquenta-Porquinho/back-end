package com.college.hotlittlepigs.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.sensors.Sensors;
import com.college.hotlittlepigs.sensors.SensorsRepository;

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
public class SensorsRepositoryTests {
    
    @Autowired private BoxRepository boxRepository;
    @Autowired private SensorsRepository sensorsRepository;

    @Test
    public void saveTest(){
        Optional<Box> requestBox = boxRepository.findById(1L);
        Box box = requestBox.get();
        
        Sensors sensors = new Sensors(null,"°C", "Sensor Temperatura Ambiente", false, box, null);
        Sensors createdSensors = sensorsRepository.save(sensors);
        assertThat(createdSensors.getId()).isEqualTo(1L);    
    }

    @Test
    public void findAllTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Sensors> page = sensorsRepository.findAll(prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void getByIdTest(){
        Optional<Sensors> result = sensorsRepository.findById(1L);
        Sensors sensors = result.get();
        assertThat(sensors.getId()).isEqualTo(1);
    }

    @Test
    public void findAllByBoxIdTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Sensors> page = sensorsRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void findAllByStatusTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Sensors> page = sensorsRepository.findAllByStatus(true, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(0);
    }
}