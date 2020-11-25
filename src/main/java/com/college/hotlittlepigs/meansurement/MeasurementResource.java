package com.college.hotlittlepigs.meansurement;

import com.college.hotlittlepigs.meansurement.dto.MeasurementListSaveDTO;
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
@RequestMapping(value = "measurement")
@RestController
public class MeasurementResource {

  private final MeasurementService service;

  @Secured({"ROLE_ADMIN"})
  @PostMapping()
  public ResponseEntity<?> saveAll(@Valid @RequestBody MeasurementListSaveDTO listMeasurementDTO) {
    service.saveAll(listMeasurementDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("{id}")
  public ResponseEntity<PageModel<Measurement>> listAllBySensorId(
      @PathVariable("id") Long id, @RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = service.listAllBySensorId(id, pr);
    return ResponseEntity.ok(pm);
  }
}
