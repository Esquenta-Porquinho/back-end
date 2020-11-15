package com.college.hotlittlepigs.sensors;

import com.college.hotlittlepigs.exception.common.NotFoundException;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SensorsService {

  private final SensorsRepository repository;

  public Sensors save(Sensors sensor) {
    return repository.save(sensor);
  }

  public PageModel<Sensors> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Sensors> page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Sensors getById(Long id) {
    Optional<Sensors> result = repository.findById(id);
    if (result.isEmpty()) throw new NotFoundException("Sensor not found !!");

    return result.get();
  }

  public PageModel<Sensors> listAllByBox(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Sensors> page = repository.findAllByBoxId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<Sensors> listAllByStatus(Boolean status, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Sensors> page = repository.findAllByStatus(status, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Sensors updateSensors(Long id, Sensors sensor) {
    Sensors updatableSensors = this.getById(id);
    sensor.setId(updatableSensors.getId());
    return save(sensor);
  }

  public Sensors updateStatus(Long id, Boolean status) {
    Sensors sensor = this.getById(id);
    sensor.setStatus(status);
    return save(sensor);
  }
}
