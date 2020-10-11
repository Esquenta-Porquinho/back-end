package com.college.hotlittlepigs.matrix;

import java.util.Map;

import javax.validation.Valid;

import com.college.hotlittlepigs.matrix.dto.MatrixSaveDTO;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "matrix")
@AllArgsConstructor
public class MatrixResource {
    
    private MatrixService matrixService;

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PostMapping()
    public ResponseEntity<Matrix> save(@RequestBody @Valid MatrixSaveDTO matrixSaveDTO){
        Matrix matrix = matrixSaveDTO.toMatrix();
        Matrix createdMatrix = matrixService.save(matrix);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatrix);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping()
    public ResponseEntity<PageModel<Matrix>> listAll(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Matrix> pm = matrixService.listAll(pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @GetMapping("/{id}")
    public ResponseEntity<Matrix> getById(@PathVariable("id") Long id){
        Matrix matrix = matrixService.getById(id);
        return ResponseEntity.ok(matrix);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Matrix> deactivate(@PathVariable("id") Long id) {
        Matrix matrix = matrixService.updateStatus(id, false);
        return ResponseEntity.ok(matrix);
    }

    @Secured({ "ROLE_MANAGER", "ROLE_SIMPLE" })
    @PatchMapping("/activate/{id}")
    public ResponseEntity<Matrix> activate(@PathVariable("id") Long id) {
        Matrix matrix = matrixService.updateStatus(id, true);
        return ResponseEntity.ok(matrix);
    }
}
