package com.college.hotlittlepigs.box;

import java.util.Optional;

import com.college.hotlittlepigs.box.dto.BoxUpdateDTO;
import com.college.hotlittlepigs.exception.NotFoundException;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BoxService {
    private BoxRepository boxRepository;

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
}
