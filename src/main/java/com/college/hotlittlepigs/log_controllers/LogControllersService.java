package com.college.hotlittlepigs.log_controllers;

import com.college.hotlittlepigs.controller.Controller;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class LogControllersService {

  private final LogControllersRepository repository;

  public LogController save(Boolean status, Controller controller) {
    var logController = new LogController();
    logController.setController(controller);
    logController.setDatetime(new Date());
    logController.setStatus(status);

    return repository.save(logController);
  }

  public PageModel<LogController> listAllByControllerId(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAllByControllerId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }
}
