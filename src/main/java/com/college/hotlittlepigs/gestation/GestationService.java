package com.college.hotlittlepigs.gestation;

import com.college.hotlittlepigs.gestation.expcetion.GestationNotFound;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GestationService {
  private final GestationRepository repository;

  public Gestation save(Gestation gestation) {
    return repository.save(gestation);
  }

  public PageModel<Gestation> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Gestation getById(Long id) {
    var gestation = repository.findById(id);
    return gestation.orElseThrow(GestationNotFound::new);
  }

  public Gestation updateGestation(Long id, Gestation gestation) {
    gestation.setId(id);
    return save(gestation);
  }

  public Gestation updateStatus(Long id, Boolean status) {
    var gestation = getById(id);
    gestation.setStatus(status);
    return save(gestation);
  }

  public PageModel<Gestation> listAllByMatrix(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAllByMatrixId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<Gestation> listAllByBox(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAllByBoxId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }
}
