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
import org.springframework.data.domain.Page;
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

import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository repository;
  private final AccessManager accessManager;

  public int updateRole(UserUpdateRoleDTO user, Long id) {

    Optional<User> result = repository.findById(id);
    if (result.isEmpty()) throw new NotFoundException("User not found !");

    if (!accessManager.isAdmin()) {
      User checkUser = result.get();
      if (checkUser.getRole() == Role.ADMIN || user.getRole() == Role.ADMIN)
        throw new AccessDeniedException("Access Denied");
    }

    return repository.updateRole(id, user.getRole());
  }

  public User save(User user) {
    String hash = HashUtil.getSecureHash(user.getPassword());
    user.setPassword(hash);
    user.setRole(Role.ASPIRANT);
    return repository.save(user);
  }

  public User update(User user) {
    return repository.save(user);
  }

  public User getById(Long id) {
    Optional<User> result = repository.findById(id);

    return result.orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public User getByEmail(String email) {
    Optional<User> result = repository.findByEmail(email);

    return result.orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public PageModel<User> listAll(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<User> page = repository.findAll(pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  public PageModel<User> listAllNotAdmin(PageRequestModel pr) {
    Pageable pageable = pr.toSpringPageRequest();
    Page<User> page = repository.findAllNotAdmin(Role.ADMIN, pageable);

    return new PageModel<>(
        (int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = getByEmail(username);

    List<GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), authorities);
  }

  public UserLoginServiceDTO login(org.springframework.security.core.userdetails.User userSpring) {
    String email = userSpring.getUsername();
    List<String> roles =
        userSpring.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    return new UserLoginServiceDTO(email, roles);
  }

  public void sendWarnings(Controller controller) {
    List<User> users = repository.findAllByRole(Role.ADMIN);

    for (User user : users) {
      JavaMailSenderImpl sender = new JavaMailSenderImpl();
      sender.setHost("To define");
      sender.setPort(0);

      MimeMessage message = sender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);
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
