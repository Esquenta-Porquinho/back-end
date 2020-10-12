package com.college.hotlittlepigs.box;

import java.util.Map;

import javax.validation.Valid;

import com.college.hotlittlepigs.box.dto.BoxSaveDTO;
import com.college.hotlittlepigs.box.dto.BoxUpdateDTO;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.parameters.Parameters;

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

@RequestMapping(value = "box")
@AllArgsConstructor
@RestController
public class BoxResource {
    
    private BoxService boxService;
    
    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PreAuthorize("@logHandler.saveLog('Create new Box')")
    @PostMapping()
    public ResponseEntity<Box> save(@Valid @RequestBody BoxSaveDTO boxDTO){
        Box box = boxDTO.toBox();
        Box newBox = boxService.save(box);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBox);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping()
    public ResponseEntity<PageModel<Box>> listAll(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Box> pm = boxService.listAll(pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping("/{id}")
    public ResponseEntity<Box> getById(@PathVariable("id") Long id){
        Box box = boxService.getById(id);
        return ResponseEntity.ok(box);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PreAuthorize("@logHandler.saveLog('Update box '+#id)")
    @PutMapping("/{id}")
    public ResponseEntity<Box> updateBox(
        @PathVariable("id") Long id,
        @Valid @RequestBody BoxUpdateDTO boxDTO
    ){
        Box updatedBox = boxService.updateBox(id, boxDTO);
        return ResponseEntity.ok(updatedBox);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PreAuthorize("@logHandler.saveLog('Deactivate box '+'#id')")
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Box> deactivate(@PathVariable("id") Long id) {
        Box box = boxService.updateStatus(id, false);
        return ResponseEntity.ok(box);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PreAuthorize("@logHandler.saveLog('Activate box '+'#id')")
    @PatchMapping("/activate/{id}")
    public ResponseEntity<Box> activate(@PathVariable("id") Long id) {
        Box box = boxService.updateStatus(id, true);
        return ResponseEntity.ok(box);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/{number}/parameters")
    public ResponseEntity<Parameters> getParameters(@PathVariable("number") int number){
        Parameters parameters = boxService.getParameters(number);
        return ResponseEntity.ok(parameters);
    }
}
