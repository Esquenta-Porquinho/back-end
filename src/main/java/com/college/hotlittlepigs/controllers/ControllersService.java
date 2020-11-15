package com.college.hotlittlepigs.controllers;

import com.college.hotlittlepigs.controllers.exception.ControllerNotFoundException;
import com.college.hotlittlepigs.log_controllers.LogControllersService;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    Page<Controller> page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<Controller> listAllByStatus(Boolean status, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Controller> page = repository.findAllByStatus(status, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<Controller> listAllByBoxId(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Controller> page = repository.findAllByBoxId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public Controller getById(Long id) {
    Optional<Controller> result = repository.findById(id);
    if (result.isEmpty()) throw new ControllerNotFoundException();
    return result.get();
  }

  public Controller update(Long id, Controller controller) {
    Controller updatableController = this.getById(id);
    controller.setId(updatableController.getId());
    return save(controller);
  }

  public Controller updateStatus(Long id, Boolean status) {
    Controller controller = this.getById(id);
    if (!status) userService.sendWarnings(controller);
    controller.setStatus(status);
    return save(controller);
  }

  public Controller updateWork(Long id, Boolean work) {
    Controller controller = this.getById(id);
    log.save(work, controller);
    return save(controller);
  }
}
