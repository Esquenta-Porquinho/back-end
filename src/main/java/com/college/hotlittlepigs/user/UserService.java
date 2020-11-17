package com.college.hotlittlepigs.user;

import com.college.hotlittlepigs.controllers.Controller;
import com.college.hotlittlepigs.exception.common.NotFoundException;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.security.AccessManager;
import com.college.hotlittlepigs.user.dto.UserLoginServiceDTO;
import com.college.hotlittlepigs.user.dto.UserUpdateRoleDTO;
import com.college.hotlittlepigs.user.enums.Role;
import com.college.hotlittlepigs.user.util.HashUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository repository;
  private final AccessManager accessManager;

  public int updateRole(UserUpdateRoleDTO user, Long id) {

    var result = repository.findById(id);
    if (result.isEmpty()) throw new NotFoundException("User not found !");

    if (!accessManager.isAdmin()) {
      var checkUser = result.get();
      if (checkUser.getRole() == Role.ADMIN || user.getRole() == Role.ADMIN)
        throw new AccessDeniedException("Access Denied");
    }

    return repository.updateRole(id, user.getRole());
  }

  public User save(User user) {
    var hash = HashUtil.getSecureHash(user.getPassword());
    user.setPassword(hash);
    user.setRole(Role.ASPIRANT);
    return repository.save(user);
  }

  public User update(User user) {
    return repository.save(user);
  }

  public User getById(Long id) {
    var result = repository.findById(id);

    return result.orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public User getByEmail(String email) {
    var result = repository.findByEmail(email);

    return result.orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public PageModel<User> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<User> listAllNotAdmin(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    var page = repository.findAllNotAdmin(Role.ADMIN, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = getByEmail(username);

    List<GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), authorities);
  }

  public UserLoginServiceDTO login(org.springframework.security.core.userdetails.User userSpring) {
    var email = userSpring.getUsername();
    var roles =
        userSpring.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    return new UserLoginServiceDTO(email, roles);
  }

  public void sendWarnings(Controller controller) {
    var users = repository.findAllByRole(Role.ADMIN);

    for (var user : users) {
      var sender = new JavaMailSenderImpl();
      sender.setHost("To define");
      sender.setPort(0);

      var message = sender.createMimeMessage();
      var helper = new MimeMessageHelper(message);
      try {
        helper.setFrom("Esquenta-Porquinho");
        helper.setSubject("Error on controller" + controller.getName());
        helper.setTo(user.getEmail());
        sender.send(message);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
