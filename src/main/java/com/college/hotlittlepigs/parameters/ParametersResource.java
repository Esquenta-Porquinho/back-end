package com.college.hotlittlepigs.parameters;

import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.parameters.dto.ParametersSaveDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@AllArgsConstructor
@RequestMapping(value = "parameters")
@RestController
public class ParametersResource {

  private final ParametersService service;

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Create new Parameters')")
  @PostMapping()
  public ResponseEntity<Parameters> save(@Valid @RequestBody ParametersSaveDTO parametersSaveDTO) {
    Parameters parameters = parametersSaveDTO.toParameters();
    Parameters createdParameters = service.save(parameters);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdParameters);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping()
  public ResponseEntity<PageModel<Parameters>> listAll(@RequestParam Map<String, String> params) {
    PageRequestModel pr = new PageRequestModel(params);
    PageModel<Parameters> pm = service.listAll(pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping("/box/{id}")
  public ResponseEntity<PageModel<Parameters>> listAllByBox(
      @PathVariable("id") Long id, @RequestParam Map<String, String> params) {
    PageRequestModel pr = new PageRequestModel(params);
    PageModel<Parameters> pm = service.listAllByBox(id, pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping("/{id}")
  public ResponseEntity<Parameters> getById(@PathVariable("id") Long id) {
    Parameters parameters = service.getById(id);
    return ResponseEntity.ok(parameters);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Update parameters ' + #id)")
  @PutMapping("/{id}")
  public ResponseEntity<Parameters> updateParameters(
      @PathVariable("id") Long id, @Valid @RequestBody ParametersSaveDTO parametersSaveDTO) {
    Parameters parameters = parametersSaveDTO.toParameters();
    Parameters createdParameters = service.updateParameters(id, parameters);
    return ResponseEntity.ok(createdParameters);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Deactivate parameters' + #id)")
  @PatchMapping("/deactivate/{id}")
  public ResponseEntity<Parameters> deactivate(@PathVariable("id") Long id) {
    Parameters parameters = service.updateStatus(id, false);
    return ResponseEntity.ok(parameters);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Activate parameters' + #id)")
  @PatchMapping("/activate/{id}")
  public ResponseEntity<Parameters> activate(@PathVariable("id") Long id) {
    Parameters parameters = service.updateStatus(id, true);
    return ResponseEntity.ok(parameters);
  }
}
