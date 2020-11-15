package com.college.hotlittlepigs.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;


import com.college.hotlittlepigs.meansurement.Measurement;
import com.college.hotlittlepigs.meansurement.MeasurementRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
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
public class MeasurementRepositoryTests {
    
    @Autowired private MeasurementRepository measurementRepository;
    @Autowired private SensorsRepository sensorsRepository;
    @Test
    public void saveTest(){
        Optional<Sensors> result = sensorsRepository.findById(1L);
        Sensors sensor = result.get();
        
        Measurement measure = new Measurement(null, 34.00, sensor);
        Measurement createdMeasurement = measurementRepository.save(measure);
        assertThat(createdMeasurement.getId()).isEqualTo(1L);    
    }

    @Test
    public void findAllBySensorsIdTest(){
        PageRequestModel prm = new PageRequestModel();
        Page<Measurement> page = measurementRepository.findAllBySensorId(1L, prm.toSpringPageRequest());
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

}