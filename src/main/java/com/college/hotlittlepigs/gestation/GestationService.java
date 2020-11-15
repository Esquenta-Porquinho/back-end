package com.college.hotlittlepigs.gestation;

import com.college.hotlittlepigs.gestation.expcetion.GestationNotFound;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class GestationService {
  private final GestationRepository repository;

  public Gestation save(Gestation gestation) {
    return repository.save(gestation);
  }

  public PageModel<Gestation> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Gestation> page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Gestation getById(Long id) {
    Optional<Gestation> result = repository.findById(id);
    if (result.isEmpty()) throw new GestationNotFound();

    return result.get();
  }

  public Gestation updateGestation(Long id, Gestation gestation) {
    gestation.setId(id);
    return save(gestation);
  }

  public Gestation updateStatus(Long id, Boolean status) {
    Gestation gestation = getById(id);
    gestation.setStatus(status);
    return save(gestation);
  }

  public PageModel<Gestation> listAllByMatrix(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Gestation> page = repository.findAllByMatrixId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<Gestation> listAllByBox(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Gestation> page = repository.findAllByBoxId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }
}
