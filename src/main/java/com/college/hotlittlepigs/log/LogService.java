package com.college.hotlittlepigs.log;

import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.user.User;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class LogService {
    private LogRepository logRepository;

    public Log save(String action, User owner){
        Log log = new Log();
        log.setOwner_id(owner);
        log.setDatetime(new Date());
        log.setDescription(action);

        Log newLog = logRepository.save(log);
        return newLog;
    }

    public PageModel<Log> listAllByOwnerId(Long id, PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<Log> page = logRepository.findAllByOwnerId(id, pageable);

        PageModel<Log> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
        return pm;
    }

}
