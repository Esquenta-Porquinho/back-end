package com.college.hotlittlepigs.log_controllers;

import java.util.Date;

import com.college.hotlittlepigs.controllers.Controllers;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LogControllersService {
    
    private LogControllersRepository logControllersRepository;

    public LogControllers save(Boolean status, Controllers controller){
        LogControllers logControllers = new LogControllers();
        logControllers.setController(controller);
        logControllers.setDatetime(new Date());
        logControllers.setStatus(status);

        LogControllers newLogControllers = logControllersRepository.save(logControllers);
        return newLogControllers;
    }

    public PageModel<LogControllers> listAllByControllerId(Long id, PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<LogControllers> page = logControllersRepository.findAllByControllerId(id, pageable);

        PageModel<LogControllers> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
        return pm;
    }

}
