package com.college.hotlittlepigs.parameters;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.parameters.expcetion.ParametersNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ParametersService {

  private final ParametersRepository repository;

  public Parameters save(Parameters parameters) {
    return repository.save(parameters);
  }

  public PageModel<Parameters> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Parameters getById(Long id) {
    var result = repository.findById(id);
    if (result.isEmpty()) throw new ParametersNotFoundException();

    return result.get();
  }

  public PageModel<Parameters> listAllByBox(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAllByBoxId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Parameters updateParameters(Long id, Parameters parameters) {
    var updatableParameters = this.getById(id);
    parameters.setId(updatableParameters.getId());
    return save(parameters);
  }

  public Parameters updateStatus(Long id, Boolean status) {
    var parameters = this.getById(id);
    parameters.setStatus(status);
    return save(parameters);
  }

  public Parameters geyByActiveBox(Box box, Double week) {
    var result = repository.findByBoxIdAndWeeksAndStatusIsTrue(box.getId(), week);
    if (result.isEmpty()) throw new ParametersNotFoundException();
    return result.get();
  }
}
