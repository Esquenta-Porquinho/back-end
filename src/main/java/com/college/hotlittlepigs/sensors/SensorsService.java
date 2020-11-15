package com.college.hotlittlepigs.sensors;

import java.util.Optional;

import com.college.hotlittlepigs.exception.common.NotFoundException;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SensorsService {
    
    private SensorsRepository sensorsRepository;

    public Sensors save(Sensors sensor){
        Sensors createdSensor = sensorsRepository.save(sensor);
        return createdSensor;
    }

    public PageModel<Sensors> listAll(PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Sensors> page = sensorsRepository.findAll(pageable);

        PageModel<Sensors> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public Sensors getById(Long id){
        Optional<Sensors> result = sensorsRepository.findById(id);
        if(!result.isPresent()) throw new NotFoundException("Sensor not found !!");

        return result.get();
    }

    public PageModel<Sensors> listAllByBox(Long id, PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Sensors> page = sensorsRepository.findAllByBoxId(id, pageable);

        PageModel<Sensors> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public PageModel<Sensors> listAllByStatus(Boolean status, PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Sensors> page = sensorsRepository.findAllByStatus(status, pageable);

        PageModel<Sensors> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public Sensors updateSensors(Long id, Sensors sensor){
        Sensors updatableSensors = this.getById(id);
        sensor.setId(updatableSensors.getId());
        return this.save(sensor);
    }

    public Sensors updateStatus(Long id, Boolean status){
        Sensors sensor = this.getById(id);
        sensor.setStatus(status);
        Sensors newSensors = this.save(sensor);
        return newSensors;
    }

}
