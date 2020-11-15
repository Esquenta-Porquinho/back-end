package com.college.hotlittlepigs.controllers;

import com.college.hotlittlepigs.controllers.dto.ControllersSaveDTO;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@AllArgsConstructor
@RequestMapping(value = "controllers")
@RestController
public class ControllersResource {

  private final ControllersService service;

  @Secured({"ROLE_ADMIN"})
  @PostMapping()
  public ResponseEntity<Controller> save(@Valid @RequestBody ControllersSaveDTO controllerDTO) {
    Controller controller = controllerDTO.toController();
    Controller createdController = service.save(controller);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdController);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/{id}")
  public ResponseEntity<Controller> getById(@PathVariable("id") Long id) {
    Controller controller = service.getById(id);
    return ResponseEntity.ok(controller);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping()
  public ResponseEntity<PageModel<Controller>> listAll(@RequestParam Map<String, String> params) {
    PageRequestModel pr = new PageRequestModel(params);
    PageModel<Controller> pm = service.listAll(pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/box/{id}")
  public ResponseEntity<PageModel<Controller>> listAllByBoxId(
      @PathVariable("id") Long id, @RequestParam Map<String, String> params) {
    PageRequestModel pr = new PageRequestModel(params);
    // TODO Jean, acho que aqui deveria ser
    //  PageModel<Controllers> pam = service.listAllByBoxId(id, pr);
    PageModel<Controller> pm = service.listAll(pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/status/active")
  public ResponseEntity<PageModel<Controller>> listAllActiveStatus(
      @RequestParam Map<String, String> params) {
    PageRequestModel pr = new PageRequestModel(params);
    PageModel<Controller> pm = service.listAllByStatus(true, pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/status/inactive")
  public ResponseEntity<PageModel<Controller>> listAllInactiveStatus(
      @RequestParam Map<String, String> params) {
    PageRequestModel pr = new PageRequestModel(params);
    PageModel<Controller> pm = service.listAllByStatus(false, pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_ADMIN"})
  @PatchMapping("/active/status/{id}")
  public ResponseEntity<Controller> activateStatus(@PathVariable("id") Long id) {
    Controller controller = service.updateStatus(id, true);
    return ResponseEntity.ok(controller);
  }

  @Secured({"ROLE_ADMIN"})
  @PatchMapping("/deactivate/status/{id}")
  public ResponseEntity<Controller> deactivateStatus(@PathVariable("id") Long id) {
    Controller controller = service.updateStatus(id, false);
    return ResponseEntity.ok(controller);
  }

  @Secured({"ROLE_ADMIN"})
  @PatchMapping("/active/work/{id}")
  public ResponseEntity<Controller> activateWork(@PathVariable("id") Long id) {
    Controller controller = service.updateWork(id, true);
    return ResponseEntity.ok(controller);
  }

  @Secured({"ROLE_ADMIN"})
  @PatchMapping("/deactivate/work/{id}")
  public ResponseEntity<Controller> deactivateWork(@PathVariable("id") Long id) {
    Controller controller = service.updateWork(id, false);
    return ResponseEntity.ok(controller);
  }

  @Secured({"ROLE_ADMIN"})
  @PutMapping("/{id}")
  public ResponseEntity<Controller> update(
      @PathVariable("id") Long id, @Valid @RequestBody ControllersSaveDTO controllersSaveDTO) {
    Controller controller = controllersSaveDTO.toController();
    Controller updateController = service.update(id, controller);
    return ResponseEntity.ok(updateController);
  }
}
