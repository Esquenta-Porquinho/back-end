package com.college.hotlittlepigs.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import com.college.hotlittlepigs.controllers.Controller;
import com.college.hotlittlepigs.exception.common.NotFoundException;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.security.AccessManager;
import com.college.hotlittlepigs.user.dto.UserLoginServiceDTO;
import com.college.hotlittlepigs.user.dto.UserUpdateRoleDTO;
import com.college.hotlittlepigs.user.enums.Role;
import com.college.hotlittlepigs.user.util.HashUtil;

import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    public User getByEmail(String email) {
        Optional<User> result = userRepository.findByEmail(email);

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
    
    public UserLoginServiceDTO login(org.springframework.security.core.userdetails.User userSpring){
        String email = userSpring.getUsername();
        List<String> roles = userSpring.getAuthorities()
                                        .stream()
                                        .map(authority -> authority.getAuthority())
                                        .collect(Collectors.toList());
        return new UserLoginServiceDTO(email, roles);
    }

    public void sendWarnings(Controller controller){
        List<User> users = userRepository.findAllByRole(Role.ADMIN);

        for(int i=0; i<users.size(); i++){
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost("To define");
            sender.setPort(00);

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try{
                helper.setFrom("Esquenta-Porquinho");
                helper.setSubject("Error on controller" + controller.getName());
                helper.setTo(users.get(i).getEmail());
                sender.send(message);
            }catch(Exception e){
                System.out.println(e.getMessage());
                continue;
            }
        };

    }
}