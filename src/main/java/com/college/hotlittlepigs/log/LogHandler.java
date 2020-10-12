package com.college.hotlittlepigs.log;

import java.util.Optional;

import com.college.hotlittlepigs.exception.NotFoundException;
import com.college.hotlittlepigs.user.User;
import com.college.hotlittlepigs.user.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("logHandler")
public class LogHandler {
    
    private LogService logService;
    private UserRepository userRepository;

    public Boolean saveLog(String action){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> result = userRepository.findByEmail(email);
        System.out.println(action);
        if(!result.isPresent()) throw new NotFoundException("There is no user with email "+ email);
        
        try {
            logService.save(action, result.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
