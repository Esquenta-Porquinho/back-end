package com.college.hotlittlepigs.meansurement;

import java.util.List;

import com.college.hotlittlepigs.meansurement.dto.MeasurementListSaveDTO;
import com.college.hotlittlepigs.meansurement.dto.MeasurementSaveDTO;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MeasurementService {
    
    private MeasurementRepository measurementRepository;

    public void save(MeasurementListSaveDTO measureListDTO){
        List<MeasurementSaveDTO> listMeasureDTO = measureListDTO.getList();

        listMeasureDTO.forEach(measureDTO->{
            Measurement measure = measureDTO.toMeasurement();
            measurementRepository.save(measure);   
        });
    }

    public PageModel<Measurement> listAllBySensorId(Long id, PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Measurement> page = measurementRepository.findAllBySensorId(id, pageable);

        PageModel<Measurement> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }
}