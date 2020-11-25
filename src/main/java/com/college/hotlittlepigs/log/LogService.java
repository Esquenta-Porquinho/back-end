package com.college.hotlittlepigs.log;

import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.user.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class LogService {

  private final LogRepository repository;

  public Log save(String action, User owner) {
    Log log = new Log();
    log.setOwner(owner);
    log.setDatetime(new Date());
    log.setDescription(action);

    return repository.save(log);
  }

  public PageModel<Log> listAllLogsByOwner(Long id, PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<Log> page = repository.findAllByOwnerId(id, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }
}
