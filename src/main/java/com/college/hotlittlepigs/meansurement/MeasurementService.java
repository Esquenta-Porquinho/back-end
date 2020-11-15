package com.college.hotlittlepigs.meansurement;

import com.college.hotlittlepigs.meansurement.dto.MeasurementListSaveDTO;
import com.college.hotlittlepigs.meansurement.dto.MeasurementSaveDTO;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MeasurementService {

  private final MeasurementRepository repository;

  public void saveAll(MeasurementListSaveDTO measureListDTO) {
    measureListDTO.getList().forEach(this::save);
  }

  private void save(MeasurementSaveDTO measurementSaveDTO) {
    repository.save(measurementSaveDTO.toMeasurement());
  }

  public PageModel<Measurement> listAllBySensorId(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Measurement> page = repository.findAllBySensorId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }
}
