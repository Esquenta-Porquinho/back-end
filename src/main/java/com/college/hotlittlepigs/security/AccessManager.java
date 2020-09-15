package com.college.hotlittlepigs.security;

import java.util.Optional;

import com.college.hotlittlepigs.exception.NotFoundException;
import com.college.hotlittlepigs.user.User;
import com.college.hotlittlepigs.user.UserRepository;
import com.college.hotlittlepigs.user.enums.Role;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component("accessManager")
@AllArgsConstructor
public class AccessManager {
    
    private UserRepository userRepository;

    public boolean isOwner(Long id){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> result = userRepository.findByEmail(email);
        
        if(!result.isPresent()) throw new NotFoundException("There is no user with email "+ email);
        
        User user = result.get();
        return user.getId() == id;
    }

    public boolean isAdmin(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> result = userRepository.findByEmail(email);
        
        if(!result.isPresent()) throw new NotFoundException("There is no user with email "+ email);
        
        User user = result.get();
        return user.getRole() == Role.ADMIN;
    }
}