package com.college.hotlittlepigs.controllers;

import com.college.hotlittlepigs.controllers.exception.ControllerNotFoundException;
import com.college.hotlittlepigs.log_controllers.LogControllersService;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ControllersService {

  private final ControllersRepository repository;
  private final LogControllersService log;
  private final UserService userService;

  public Controller save(Controller controller) {
    return repository.save(controller);
  }

  public PageModel<Controller> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<Controller> listAllByStatus(Boolean status, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAllByStatus(status, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<Controller> listAllByBoxId(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAllByBoxId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Controller getById(Long id) {
    var result = repository.findById(id);
    if (result.isEmpty()) throw new ControllerNotFoundException();
    return result.get();
  }

  public Controller update(Long id, Controller controller) {
    var updatableController = this.getById(id);
    controller.setId(updatableController.getId());
    return save(controller);
  }

  public Controller updateStatus(Long id, Boolean status) {
    var controller = this.getById(id);
    if (!status) userService.sendWarnings(controller);
    controller.setStatus(status);
    return save(controller);
  }

  public Controller updateWork(Long id, Boolean work) {
    var controller = this.getById(id);
    log.save(work, controller);
    return save(controller);
  }
}
