package com.college.hotlittlepigs.gestation;

import java.util.Map;

import javax.validation.Valid;

import com.college.hotlittlepigs.gestation.dto.GestationSaveDTO;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

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
@RequestMapping(value = "gestation")
@Controller
public class GestationResource {
    private GestationService gestationService;

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PostMapping()
    public ResponseEntity<Gestation> save(@Valid @RequestBody GestationSaveDTO gestatioDTO) {
        Gestation gestation = gestatioDTO.toGestation();
        Gestation newGestation = gestationService.save(gestation);
        return ResponseEntity.ok(newGestation);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping()
    public ResponseEntity<PageModel<Gestation>> listAll(@RequestParam Map<String, String> params) {
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Gestation> pm = gestationService.listAll(pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping("/{id}")
    public ResponseEntity<Gestation> getById(@PathVariable("id") Long id) {
        Gestation gestation = gestationService.getById(id);
        return ResponseEntity.ok(gestation);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping("/box/{id}")
    public ResponseEntity<PageModel<Gestation>> listAllByBox(
        @PathVariable("id") Long id,
        @RequestParam Map<String, String> params
    ) {
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Gestation> pm = gestationService.listAllByBox(id, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping("/matrix/{id}")
    public ResponseEntity<PageModel<Gestation>> listAllByMatrix(
        @PathVariable("id") Long id,
        @RequestParam Map<String, String> params
    ) {
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Gestation> pm = gestationService.listAllByMatrix(id, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PutMapping("/{id}")
    public ResponseEntity<Gestation> updateGestation(
        @PathVariable("id") Long id,
        @Valid @RequestBody GestationSaveDTO gestationDTO
    ) {
        Gestation gestation = gestationDTO.toGestation();
        Gestation newGestation = gestationService.updateGestation(id, gestation);
        return ResponseEntity.ok(newGestation); 
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Gestation> deactivate(@PathVariable("id") Long id) {
        Gestation gestation = gestationService.updateStatus(id, false);
        return ResponseEntity.ok(gestation);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
    @PatchMapping("/activate/{id}")
    public ResponseEntity<Gestation> activate(@PathVariable("id") Long id) {
        Gestation gestation = gestationService.updateStatus(id, true);
        return ResponseEntity.ok(gestation);
    }
}
