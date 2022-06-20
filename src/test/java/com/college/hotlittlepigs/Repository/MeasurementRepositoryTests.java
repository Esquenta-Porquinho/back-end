package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.meansurement.Measurement;
import com.college.hotlittlepigs.meansurement.MeasurementRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.sensors.SensorsRepository;
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
public class MeasurementRepositoryTests {

  @Autowired private MeasurementRepository measurementRepository;
  @Autowired private SensorsRepository sensorsRepository;

  @Test
  public void saveTest() {
    var result = sensorsRepository.findById(1L);
    var sensor = result.get();

    var measure = new Measurement(null, 34.00, sensor);
    var createdMeasurement = measurementRepository.save(measure);
    assertThat(createdMeasurement.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllBySensorsIdTest() {
    var prm = new PageRequestModel();
    var page = measurementRepository.findAllBySensorId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }
}
