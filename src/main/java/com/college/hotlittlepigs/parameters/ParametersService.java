package com.college.hotlittlepigs.parameters;

import java.util.Optional;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.exception.common.NotFoundException;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ParametersService {
    
    private ParametersRepository parametersRepository;

    public Parameters save(Parameters parameters){
        Parameters newParameters = parametersRepository.save(parameters);
        return newParameters;
    }

    public PageModel<Parameters> listAll(PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Parameters> page = parametersRepository.findAll(pageable);

        PageModel<Parameters> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public Parameters getById(Long id){
        Optional<Parameters> result = parametersRepository.findById(id);
        if(!result.isPresent()) throw new NotFoundException("Parameters not found !!");

        return result.get();
    }
    
    public PageModel<Parameters> listAllByBox(Long id, PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Parameters> page = parametersRepository.findAllByBoxId(id, pageable);

        PageModel<Parameters> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public Parameters updateParameters(Long id, Parameters parameters){
        Parameters updatableParameters = this.getById(id);
        parameters.setId(updatableParameters.getId());
        return this.save(parameters);
    }

    public Parameters updateStatus(Long id, Boolean status){
        Parameters parameters = this.getById(id);
        parameters.setStatus(status);
        Parameters newParameters = this.save(parameters);
        return newParameters;
    }

    public Parameters getParametersByActiveByBox(Box box, Double week){
        Optional<Parameters> result = parametersRepository.findActiveByWeekByBox(box.getId(), week);
        if(!result.isPresent()) throw new NotFoundException("Parameters not found !!");
        return result.get();
    }
}
