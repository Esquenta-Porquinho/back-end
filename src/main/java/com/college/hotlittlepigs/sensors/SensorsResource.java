package com.college.hotlittlepigs.sensors;

import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.sensors.dto.SensorsSaveDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@AllArgsConstructor
@RequestMapping(value = "sensors")
@RestController
public class SensorsResource {
  private final SensorsService service;

  @Secured({"ROLE_ADMIN"})
  @PostMapping()
  public ResponseEntity<Sensors> save(@Valid @RequestBody SensorsSaveDTO sensorsSaveDTO) {
    var sensors = sensorsSaveDTO.toSensors();
    var createdSensors = service.save(sensors);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdSensors);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping()
  public ResponseEntity<PageModel<Sensors>> listAll(@RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = service.listAll(pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/box/{id}")
  public ResponseEntity<PageModel<Sensors>> listAllByBox(
      @PathVariable("id") Long id, @RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = service.listAllByBox(id, pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/active")
  public ResponseEntity<PageModel<Sensors>> listAllActive(
      @RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = service.listAllByStatus(true, pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/deactive")
  public ResponseEntity<PageModel<Sensors>> listAllDeactive(
      @RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = service.listAllByStatus(true, pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/{id}")
  public ResponseEntity<Sensors> getById(@PathVariable("id") Long id) {
    var sensors = service.getById(id);
    return ResponseEntity.ok(sensors);
  }

  @Secured({"ROLE_ADMIN"})
  @PutMapping("/{id}")
  public ResponseEntity<Sensors> updateSensors(
      @PathVariable("id") Long id, @Valid @RequestBody SensorsSaveDTO sensorsSaveDTO) {
    var sensors = sensorsSaveDTO.toSensors();
    var createdSensors = service.updateSensors(id, sensors);
    return ResponseEntity.ok(createdSensors);
  }

  @Secured({"ROLE_ADMIN"})
  @PatchMapping("/deactivate/{id}")
  public ResponseEntity<Sensors> deactivate(@PathVariable("id") Long id) {
    var sensors = service.updateStatus(id, false);
    return ResponseEntity.ok(sensors);
  }

  @Secured({"ROLE_ADMIN"})
  @PatchMapping("/activate/{id}")
  public ResponseEntity<Sensors> activate(@PathVariable("id") Long id) {
    var sensors = service.updateStatus(id, true);
    return ResponseEntity.ok(sensors);
  }
}
