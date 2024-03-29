package com.college.hotlittlepigs.box;

import com.college.hotlittlepigs.box.dto.BoxSaveDTO;
import com.college.hotlittlepigs.box.dto.BoxUpdateDTO;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.parameters.Parameters;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping(value = "box")
@AllArgsConstructor
@RestController
public class BoxResource {

  private final BoxService service;

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Create new Box')")
  @PostMapping()
  public ResponseEntity<Box> save(@Valid @RequestBody BoxSaveDTO boxDTO) {
    var box = boxDTO.toBox();
    var newBox = service.save(box);
    return ResponseEntity.status(HttpStatus.CREATED).body(newBox);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping()
  public ResponseEntity<PageModel<Box>> listAll(@RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = service.listAll(pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping("/{id}")
  public ResponseEntity<Box> getById(@PathVariable("id") Long id) {
    var box = service.getById(id);
    return ResponseEntity.ok(box);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Update box '+#id)")
  @PutMapping("/{id}")
  public ResponseEntity<Box> updateBox(
      @PathVariable("id") Long id, @Valid @RequestBody BoxUpdateDTO boxDTO) {
    var updatedBox = service.updateBox(id, boxDTO);
    return ResponseEntity.ok(updatedBox);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Deactivate box '+'#id')")
  @PatchMapping("/deactivate/{id}")
  public ResponseEntity<Box> deactivate(@PathVariable("id") Long id) {
    var box = service.updateStatus(id, false);
    return ResponseEntity.ok(box);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Activate box '+'#id')")
  @PatchMapping("/activate/{id}")
  public ResponseEntity<Box> activate(@PathVariable("id") Long id) {
    var box = service.updateStatus(id, true);
    return ResponseEntity.ok(box);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/{number}/parameters")
  public ResponseEntity<Parameters> getParameters(@PathVariable("number") int number) {
    var parameters = service.getParameters(number);
    return ResponseEntity.ok(parameters);
  }
}
