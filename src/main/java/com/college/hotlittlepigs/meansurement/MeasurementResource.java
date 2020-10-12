package com.college.hotlittlepigs.meansurement;

import java.util.Map;

import javax.validation.Valid;

import com.college.hotlittlepigs.meansurement.dto.MeasurementListSaveDTO;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "measurement")
@RestController
public class MeasurementResource {
    
    private MeasurementService measurementService;

    @Secured({ "ROLE_ADMIN" })
    @PostMapping()
    public ResponseEntity<?> save(@Valid @RequestBody MeasurementListSaveDTO listMeasurementDTO){
        measurementService.save(listMeasurementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("{id}")
    public ResponseEntity<PageModel<Measurement>> listAllBySensorId(
        @PathVariable("id") Long id,
        @RequestParam Map<String, String> params
    ){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Measurement> pm = measurementService.listAllBySensorId(id, pr);
        return ResponseEntity.ok(pm);
    }
}
