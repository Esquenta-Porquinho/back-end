package com.college.hotlittlepigs.box;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.college.hotlittlepigs.box.dto.BoxUpdateDTO;
import com.college.hotlittlepigs.exception.NotFoundException;
import com.college.hotlittlepigs.gestation.Gestation;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.parameters.Parameters;
import com.college.hotlittlepigs.parameters.ParametersService;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BoxService {
    
    private BoxRepository boxRepository;
    private ParametersService parametersService;

    public Box save(Box box){
        Box newBox = boxRepository.save(box);
        return newBox;
    }
    
    public PageModel<Box> listAll(PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Box> page = boxRepository.findAll(pageable);

        PageModel<Box> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public Box getById(Long id){
        Optional<Box> result = boxRepository.findById(id);
        if(!result.isPresent()) throw new NotFoundException("Box not found !!");
        return result.get();
    }

    public Box updateBox(Long id, BoxUpdateDTO box){
        Box updatableBox = this.getById(id);
        updatableBox.setController(box.getController());
        updatableBox.setArea(box.getArea());
        Box updatedBox = boxRepository.save(updatableBox);
        return updatedBox;
    }

    public Box updateStatus(Long id, Boolean status){
        Box box = this.getById(id);
        box.setStatus(status);
        Box newBox = this.save(box);
        return newBox;
    }

    public Parameters getParameters(int number){
        Optional<Box> result = boxRepository.findByNumberByStatus(number, true);
        if(!result.isPresent()) throw new NotFoundException("Box not found !!");

        Box box = result.get();
        List<Gestation> list = box.getGestations();

        if(list.isEmpty()) throw new NotFoundException("Parameters not found !!");
        
        Gestation gestation = list.get(list.size()-1);
        Date date = gestation.getEffectiveParturition();

        DateTime today = DateTime.now();
        DateTime birthDate = new DateTime((date.getYear()+1900), (date.getMonth()+1), date.getDate(), 0, 0, 0);
        
        Days days = Days.daysBetween(birthDate, today);
        Double week = Math.ceil(days.getDays()/7.0);
        
        Parameters parameters = parametersService.getParametersByActiveByBox(box, week);
        return parameters;
    }
}
