package com.college.hotlittlepigs.controllers;

import java.util.Optional;

import com.college.hotlittlepigs.exception.NotFoundException;
import com.college.hotlittlepigs.log_controllers.LogControllersService;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ControllersService {
    
    private ControllersRepository controllersRepository;
    private LogControllersService logControllersService;

    public Controllers save(Controllers controllers){
        Controllers newController = controllersRepository.save(controllers);
        return newController;
    }

    public PageModel<Controllers> listAll(PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Controllers> page = controllersRepository.findAll(pageable);

        PageModel<Controllers> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public PageModel<Controllers> listAllByStatus(Boolean status, PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Controllers> page = controllersRepository.findAllByStatus(status, pageable);

        PageModel<Controllers> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public PageModel<Controllers> listAllByBoxId(Long id, PageRequestModel pr){
        Pageable pageable = pr.toSpringPageRequest();
        Page<Controllers> page = controllersRepository.findAllByBoxId(id, pageable);

        PageModel<Controllers> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }   

    public Controllers getById(Long id){
        Optional<Controllers> result = controllersRepository.findById(id);
        if(!result.isPresent()) throw new NotFoundException("Controller not found !!");
        return result.get();
    }

    public Controllers update(Long id, Controllers controllers){
        Controllers updatableController = this.getById(id);
        controllers.setId(updatableController.getId());
        return this.save(controllers);
    }

    public Controllers updateStatus(Long id, Boolean status){
        Controllers controllers = this.getById(id);
        controllers.setStatus(status);
        Controllers newControllers = this.save(controllers);
        return newControllers;
    }

    public Controllers updateWork(Long id, Boolean work){
        Controllers controller = this.getById(id);
        logControllersService.save(work, controller);
        Controllers newController = controllersRepository.save(controller);
        return newController;
    }
}
