package com.college.hotlittlepigs.gestation;

import com.college.hotlittlepigs.gestation.dto.GestationSaveDTO;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@AllArgsConstructor
@RequestMapping(value = "gestation")
@RestController
public class GestationResource {
  private GestationService service;

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Create new Gestation')")
  @PostMapping()
  public ResponseEntity<Gestation> save(@Valid @RequestBody GestationSaveDTO gestatioDTO) {
    Gestation gestation = gestatioDTO.toGestation();
    Gestation newGestation = service.save(gestation);
    return ResponseEntity.status(HttpStatus.CREATED).body(newGestation);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping()
  public ResponseEntity<PageModel<Gestation>> listAll(@RequestParam Map<String, String> params) {
    PageRequestModel pr = new PageRequestModel(params);
    PageModel<Gestation> pm = service.listAll(pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping("/{id}")
  public ResponseEntity<Gestation> getById(@PathVariable("id") Long id) {
    Gestation gestation = service.getById(id);
    return ResponseEntity.ok(gestation);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping("/box/{id}")
  public ResponseEntity<PageModel<Gestation>> listAllByBox(
      @PathVariable("id") Long id, @RequestParam Map<String, String> params) {
    PageRequestModel pr = new PageRequestModel(params);
    PageModel<Gestation> pm = service.listAllByBox(id, pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping("/matrix/{id}")
  public ResponseEntity<PageModel<Gestation>> listAllByMatrix(
      @PathVariable("id") Long id, @RequestParam Map<String, String> params) {
    PageRequestModel pr = new PageRequestModel(params);
    PageModel<Gestation> pm = service.listAllByMatrix(id, pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Update gestation' + #id)")
  @PutMapping("/{id}")
  public ResponseEntity<Gestation> updateGestation(
      @PathVariable("id") Long id, @Valid @RequestBody GestationSaveDTO gestationDTO) {
    Gestation gestation = gestationDTO.toGestation();
    Gestation newGestation = service.updateGestation(id, gestation);
    return ResponseEntity.ok(newGestation);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Deactivate gestation' + #id)")
  @PatchMapping("/deactivate/{id}")
  public ResponseEntity<Gestation> deactivate(@PathVariable("id") Long id) {
    Gestation gestation = service.updateStatus(id, false);
    return ResponseEntity.ok(gestation);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Activate gestation' + #id)")
  @PatchMapping("/activate/{id}")
  public ResponseEntity<Gestation> activate(@PathVariable("id") Long id) {
    Gestation gestation = service.updateStatus(id, true);
    return ResponseEntity.ok(gestation);
  }
}
