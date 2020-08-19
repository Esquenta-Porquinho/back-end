package com.college.hotlittlepigs.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.college.hotlittlepigs.exception.NotFoundException;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.security.AccessManager;
import com.college.hotlittlepigs.user.enums.Role;
import com.college.hotlittlepigs.user.util.HashUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired UserRepository userRepository;
    @Autowired AccessManager accessManager;

    public int updateRole(User user) {
        Optional<User> result = userRepository.findById(user.getId());
        if(!result.isPresent()) throw new NotFoundException("User not found !");
        
        if(!accessManager.isAdmin()){
            User checkUser = result.get();
            if(checkUser.getRole() == Role.ADMIN || user.getRole() == Role.ADMIN)
                throw new AccessDeniedException("Access Denied");
        }

        return userRepository.updateRole(user.getId(), user.getRole());
    }

    public User save(User user) {
        String hash = HashUtil.getSecureHash(user.getPassword());
        user.setPassword(hash);
        user.setRole(Role.ASPIRANT);
        User createdUser = userRepository.save(user);
        return createdUser;
    }

    public User update(User user) {
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    public User getById(Long id) {
        Optional<User> result = userRepository.findById(id);

        return result.orElseThrow(() -> new NotFoundException("User not found !"));
    }

    public PageModel<User> listAll(PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<User> page = userRepository.findAll(pageable);

        PageModel<User> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    public PageModel<User> listAllNotAdmin(PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<User> page = userRepository.findAllNotAdmin(Role.ADMIN, pageable);

        PageModel<User> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
                page.getContent());
        return pm;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(username);
        if(!result.isPresent()) throw new UsernameNotFoundException("Doesn't exist username !!");

        User user = result.get();

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_"+ user.getRole().name()));

        org.springframework.security.core.userdetails.User  UserSpring = 
                        new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

        return UserSpring;
    }
    
}