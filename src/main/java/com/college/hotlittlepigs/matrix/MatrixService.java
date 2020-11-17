package com.college.hotlittlepigs.matrix;

import com.college.hotlittlepigs.matrix.exception.MatrixNotFoundException;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MatrixService {
  private final MatrixRepository repository;

  public Matrix save(Matrix matrix) {
    return repository.save(matrix);
  }

  public PageModel<Matrix> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Matrix getById(Long id) {
    var result = repository.findById(id);
    if (result.isEmpty()) throw new MatrixNotFoundException();

    return result.get();
  }

  public Matrix updateStatus(Long id, Boolean status) {
    var matrix = this.getById(id);
    matrix.setStatus(status);
    return save(matrix);
  }
}
