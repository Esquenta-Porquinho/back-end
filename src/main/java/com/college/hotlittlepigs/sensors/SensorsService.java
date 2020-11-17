package com.college.hotlittlepigs.sensors;

import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.sensors.exception.SensorNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SensorsService {

  private final SensorsRepository repository;

  public Sensors save(Sensors sensor) {
    return repository.save(sensor);
  }

  public PageModel<Sensors> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Sensors getById(Long id) {
    var result = repository.findById(id);
    return result.orElseThrow(SensorNotFoundException::new);
  }

  public PageModel<Sensors> listAllByBox(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAllByBoxId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<Sensors> listAllByStatus(Boolean status, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAllByStatus(status, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Sensors updateSensors(Long id, Sensors sensor) {
    var updatableSensors = getById(id);
    sensor.setId(updatableSensors.getId());
    return save(sensor);
  }

  public Sensors updateStatus(Long id, Boolean status) {
    var sensor = getById(id);
    sensor.setStatus(status);
    return save(sensor);
  }
}
