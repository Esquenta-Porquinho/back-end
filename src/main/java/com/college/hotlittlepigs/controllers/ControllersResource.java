package com.college.hotlittlepigs.controllers;

import java.util.Map;

import javax.validation.Valid;

import com.college.hotlittlepigs.controllers.dto.ControllersSaveDTO;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "controllers")
@Controller
public class ControllersResource {
    
    private ControllersService controllersService;

    @Secured({ "ROLE_ADMIN" })
    @PostMapping()
    public ResponseEntity<Controllers> save(@Valid @RequestBody ControllersSaveDTO controllerDTO){
        Controllers controller = controllerDTO.toController();
        Controllers createdController = controllersService.save(controller);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdController);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/{id}")
    public ResponseEntity<Controllers> getById(@PathVariable("id") Long id){
        Controllers controllers = controllersService.getById(id);
        return ResponseEntity.ok(controllers);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping()
    public ResponseEntity<PageModel<Controllers>> listAll(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Controllers> pm = controllersService.listAll(pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/box/{id}")
    public ResponseEntity<PageModel<Controllers>> listAllByBoxId(
        @PathVariable("id") Long id,
        @RequestParam Map<String, String> params
    ){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Controllers> pm = controllersService.listAll(pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/work/active")
    public ResponseEntity<PageModel<Controllers>> listAllByActiveWork(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Controllers> pm = controllersService.listAllByWork(true, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/work/deactive")
    public ResponseEntity<PageModel<Controllers>> listAllDeactiveWork(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Controllers> pm = controllersService.listAllByWork(false, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/status/active")
    public ResponseEntity<PageModel<Controllers>> listAllActiveStatus(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Controllers> pm = controllersService.listAllByStatus(true, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/status/deactive")
    public ResponseEntity<PageModel<Controllers>> listAllDeactiveStatus(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Controllers> pm = controllersService.listAllByStatus(false, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN" })
    @PatchMapping("/active/status/{id}")
    public ResponseEntity<Controllers> activateStatus(@PathVariable("id") Long id){
        Controllers controller = controllersService.updateStatus(id, true);
        return ResponseEntity.ok(controller);
    }

    @Secured({ "ROLE_ADMIN" })
    @PatchMapping("/deactivate/status/{id}")
    public ResponseEntity<Controllers> deactivateStatus(@PathVariable("id") Long id){
        Controllers controller = controllersService.updateStatus(id, false);
        return ResponseEntity.ok(controller);
    }

    @Secured({ "ROLE_ADMIN" })
    @PatchMapping("/active/work/{id}")
    public ResponseEntity<Controllers> activateWork(@PathVariable("id") Long id){
        Controllers controller = controllersService.updateWork(id, true);
        return ResponseEntity.ok(controller);
    }

    @Secured({ "ROLE_ADMIN" })
    @PatchMapping("/deactivate/work/{id}")
    public ResponseEntity<Controllers> deactivateWork(@PathVariable("id") Long id){
        Controllers controller = controllersService.updateWork(id, false);
        return ResponseEntity.ok(controller);
    }

    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<Controllers> update(
        @PathVariable("id") Long id,
        @Valid @RequestBody ControllersSaveDTO controllersSaveDTO
    ){
        Controllers controllers = controllersSaveDTO.toController();
        Controllers updateController = controllersService.update(id, controllers);
        return ResponseEntity.ok(updateController);
    }


}
