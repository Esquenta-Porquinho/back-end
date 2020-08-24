package com.college.hotlittlepigs.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.college.hotlittlepigs.exception.NotFoundException;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.security.AccessManager;
import com.college.hotlittlepigs.user.dto.UserLoginDTO;
import com.college.hotlittlepigs.user.dto.UserLoginServiceDTO;
import com.college.hotlittlepigs.user.dto.UserUpdateRoleDTO;
import com.college.hotlittlepigs.user.enums.Role;
import com.college.hotlittlepigs.user.util.HashUtil;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private AccessManager accessManager;
    private AuthenticationManager authManager;


    public int updateRole(UserUpdateRoleDTO user, Long id) {

        Optional<User> result = userRepository.findById(id);
        if(!result.isPresent()) throw new NotFoundException("User not found !");
        
        if(!accessManager.isAdmin()){
            User checkUser = result.get();
            if(checkUser.getRole() == Role.ADMIN || user.getRole() == Role.ADMIN)
                throw new AccessDeniedException("Access Denied");
        }

        return userRepository.updateRole(id, user.getRole());
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
    
    public UserLoginServiceDTO login(UserLoginDTO user){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication auth = authManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        org.springframework.security.core.userdetails.User userSpring = 
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        String email = userSpring.getUsername();
        List<String> roles = userSpring.getAuthorities()
                                        .stream()
                                        .map(authority -> authority.getAuthority())
                                        .collect(Collectors.toList());
        return new UserLoginServiceDTO(email, roles);
    }
}