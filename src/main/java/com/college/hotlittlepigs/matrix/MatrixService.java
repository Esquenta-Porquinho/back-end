package com.college.hotlittlepigs.matrix;

import java.util.Optional;

import com.college.hotlittlepigs.exception.common.NotFoundException;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MatrixService {
    private MatrixRepository matrixRepository;

    public Matrix save(Matrix matrix) {
        Matrix createdMatrix = matrixRepository.save(matrix);
        return createdMatrix;
    }

    public PageModel<Matrix> listAll(PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Matrix> page = matrixRepository.findAll(pageable);

        PageModel<Matrix> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public Matrix getById(Long id){
        Optional<Matrix> result = matrixRepository.findById(id);
        if(!result.isPresent()) throw new NotFoundException("Matrix not found !!");

        return result.get();
    }
    
    public Matrix updateStatus(Long id, Boolean status){
        Matrix matrix = this.getById(id);
        matrix.setStatus(status);
        Matrix newMatrix = this.save(matrix);
        return newMatrix;
    }

}
