package com.college.hotlittlepigs.sensors;

import java.util.Map;

import javax.validation.Valid;

import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.sensors.dto.SensorsSaveDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "sensors")
@Controller
public class SensorsResource {
    private SensorsService sensorsService;

    @Secured({  "ROLE_ADMIN" })
    @PostMapping()
    public ResponseEntity<Sensors> save(@Valid @RequestBody SensorsSaveDTO sensorsSaveDTO){
        Sensors sensors = sensorsSaveDTO.toSensors();
        Sensors createdSensors = sensorsService.save(sensors);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSensors);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping()
    public ResponseEntity<PageModel<Sensors>> listAll(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Sensors> pm = sensorsService.listAll(pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/box/{id}")
    public ResponseEntity<PageModel<Sensors>> listAllByBox(
        @PathVariable("id") Long id,
        @RequestParam Map<String, String> params
    ){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Sensors> pm = sensorsService.listAllByBox(id, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/active")
    public ResponseEntity<PageModel<Sensors>> listAllActive(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Sensors> pm = sensorsService.listAllByStatus(true, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/deactive")
    public ResponseEntity<PageModel<Sensors>> listAllDeactive(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Sensors> pm = sensorsService.listAllByStatus(true, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/{id}")
    public ResponseEntity<Sensors> getById(@PathVariable("id") Long id){
        Sensors sensors = sensorsService.getById(id);
        return ResponseEntity.ok(sensors);
    }

    @Secured({  "ROLE_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<Sensors> updateSensors(
        @PathVariable("id") Long id,
        @Valid @RequestBody SensorsSaveDTO sensorsSaveDTO
    ){
        Sensors sensors = sensorsSaveDTO.toSensors();
        Sensors createdSensors = sensorsService.updateSensors(id, sensors);
        return ResponseEntity.ok(createdSensors);
    }

    @Secured({ "ROLE_ADMIN" })
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Sensors> deactivate(@PathVariable("id") Long id) {
        Sensors sensors = sensorsService.updateStatus(id, false);
        return ResponseEntity.ok(sensors);
    }

    @Secured({ "ROLE_ADMIN" })
    @PatchMapping("/activate/{id}")
    public ResponseEntity<Sensors> activate(@PathVariable("id") Long id) {
        Sensors sensors = sensorsService.updateStatus(id, true);
        return ResponseEntity.ok(sensors);
    }
}
