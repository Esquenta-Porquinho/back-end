package com.college.hotlittlepigs.Repository;

import com.college.hotlittlepigs.box.BoxRepository;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.sensors.Sensors;
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
public class SensorsRepositoryTests {

  @Autowired private BoxRepository boxRepository;
  @Autowired private SensorsRepository sensorsRepository;

  @Test
  public void saveTest() {
    var requestBox = boxRepository.findById(1L);
    var box = requestBox.get();

    var sensors = new Sensors(null, "°C", "Sensor Temperatura Ambiente", false, box, null);
    var createdSensors = sensorsRepository.save(sensors);
    assertThat(createdSensors.getId()).isEqualTo(1L);
  }

  @Test
  public void findAllTest() {
    var prm = new PageRequestModel();
    var page = sensorsRepository.findAll(prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void getByIdTest() {
    var result = sensorsRepository.findById(1L);
    var sensors = result.get();
    assertThat(sensors.getId()).isEqualTo(1);
  }

  @Test
  public void findAllByBoxIdTest() {
    var prm = new PageRequestModel();
    var page = sensorsRepository.findAllByBoxId(1L, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void findAllByStatusTest() {
    var prm = new PageRequestModel();
    var page = sensorsRepository.findAllByStatus(true, prm.toSpringPageRequest());
    assertThat(page.getTotalElements()).isEqualTo(0);
  }
}
