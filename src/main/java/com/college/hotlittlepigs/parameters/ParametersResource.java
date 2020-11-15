package com.college.hotlittlepigs.parameters;

import java.util.Map;

import javax.validation.Valid;

import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.parameters.dto.ParametersSaveDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value="parameters")
@RestController
public class ParametersResource {

    private ParametersService parametersService;

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PreAuthorize("@logHandler.saveLog('Create new Parameters')")
    @PostMapping()
    public ResponseEntity<Parameters> save(@Valid @RequestBody ParametersSaveDTO parametersSaveDTO){
        Parameters parameters = parametersSaveDTO.toParameters();
        Parameters createdParameters = parametersService.save(parameters);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParameters);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping()
    public ResponseEntity<PageModel<Parameters>> listAll(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Parameters> pm = parametersService.listAll(pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping("/box/{id}")
    public ResponseEntity<PageModel<Parameters>> listAllByBox(
        @PathVariable("id") Long id,
        @RequestParam Map<String, String> params
    ){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Parameters> pm = parametersService.listAllByBox(id, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping("/{id}")
    public ResponseEntity<Parameters> getById(@PathVariable("id") Long id){
        Parameters parameters = parametersService.getById(id);
        return ResponseEntity.ok(parameters);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PreAuthorize("@logHandler.saveLog('Update parameters ' + #id)")
    @PutMapping("/{id}")
    public ResponseEntity<Parameters> updateParameters(
        @PathVariable("id") Long id,
        @Valid @RequestBody ParametersSaveDTO parametersSaveDTO
    ){
        Parameters parameters = parametersSaveDTO.toParameters();
        Parameters createdParameters = parametersService.updateParameters(id, parameters);
        return ResponseEntity.ok(createdParameters);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PreAuthorize("@logHandler.saveLog('Deactivate parameters' + #id)")
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Parameters> deactivate(@PathVariable("id") Long id) {
        Parameters parameters = parametersService.updateStatus(id, false);
        return ResponseEntity.ok(parameters);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PreAuthorize("@logHandler.saveLog('Activate parameters' + #id)")
    @PatchMapping("/activate/{id}")
    public ResponseEntity<Parameters> activate(@PathVariable("id") Long id) {
        Parameters parameters = parametersService.updateStatus(id, true);
        return ResponseEntity.ok(parameters);
    }
}
