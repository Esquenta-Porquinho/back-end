package com.college.hotlittlepigs.matrix;

import com.college.hotlittlepigs.matrix.dto.MatrixSaveDTO;
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
@RequestMapping(value = "matrix")
@RestController
public class MatrixResource {

  private final MatrixService service;

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Create new matrix')")
  @PostMapping()
  public ResponseEntity<Matrix> save(@RequestBody @Valid MatrixSaveDTO matrixSaveDTO) {
    var matrix = matrixSaveDTO.toMatrix();
    var createdMatrix = service.save(matrix);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMatrix);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping()
  public ResponseEntity<PageModel<Matrix>> listAll(@RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = service.listAll(pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @GetMapping("/{id}")
  public ResponseEntity<Matrix> getById(@PathVariable("id") Long id) {
    var matrix = service.getById(id);
    return ResponseEntity.ok(matrix);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Deactivate matrix' + #id)")
  @PatchMapping("/deactivate/{id}")
  public ResponseEntity<Matrix> deactivate(@PathVariable("id") Long id) {
    var matrix = service.updateStatus(id, false);
    return ResponseEntity.ok(matrix);
  }

  @Secured({"ROLE_MANAGER", "ROLE_SIMPLE"})
  @PreAuthorize("@logHandler.saveLog('Activate matrix' + #id)")
  @PatchMapping("/activate/{id}")
  public ResponseEntity<Matrix> activate(@PathVariable("id") Long id) {
    var matrix = service.updateStatus(id, true);
    return ResponseEntity.ok(matrix);
  }
}
