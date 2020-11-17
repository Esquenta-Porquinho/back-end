package com.college.hotlittlepigs.user;

import com.college.hotlittlepigs.log.Log;
import com.college.hotlittlepigs.log.LogService;
import com.college.hotlittlepigs.pagination.PageModel;
import com.college.hotlittlepigs.pagination.PageRequestModel;
import com.college.hotlittlepigs.security.JwtManager;
import com.college.hotlittlepigs.user.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@AllArgsConstructor
@RequestMapping(value = "users")
@RestController
public class UserResource {
  private final UserService userService;
  private final LogService logService;
  private final JwtManager jwtManager;
  private final AuthenticationManager authManager;

  @PostMapping()
  public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO userDTO) {
    var user = userDTO.toUser();
    var createdUser = userService.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  @PreAuthorize("@accessManager.isOwner(#id)")
  @PutMapping("/{id}")
  public ResponseEntity<User> update(
      @PathVariable(name = "id") Long id, @RequestBody UserUpdateDTO userDTO) {
    var user = userDTO.toUser();
    user.setId(id);
    var updatedUser = userService.save(user);
    return ResponseEntity.ok(updatedUser);
  }

  @PreAuthorize("@accessManager.isOwner(#id)")
  @GetMapping("/{id}")
  public ResponseEntity<User> getById(@PathVariable("id") Long id) {
    var user = userService.getById(id);
    return ResponseEntity.ok(user);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/admin")
  public ResponseEntity<PageModel<User>> listAll(@RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = userService.listAll(pr);
    return ResponseEntity.ok(pm);
  }

  @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
  @GetMapping
  public ResponseEntity<PageModel<User>> listAllNotAdmin(@RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = userService.listAllNotAdmin(pr);
    return ResponseEntity.ok(pm);
  }

  @PostMapping("/login")
  public ResponseEntity<UserLoginResponseDTO> login(@Valid @RequestBody UserLoginDTO user) {
    var token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
    var auth = authManager.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(auth);

    var userLogin =
        userService.login((org.springframework.security.core.userdetails.User) auth.getPrincipal());

    return ResponseEntity.ok(jwtManager.createToken(userLogin.getEmail(), userLogin.getRoles()));
  }

  @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
  @PreAuthorize("@logHandler.saveLog('Update role' + #id)")
  @PatchMapping("/role/{id}")
  public ResponseEntity<?> updateRole(
      @PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdateRoleDTO userDTO) {
    userService.updateRole(userDTO, id);
    return ResponseEntity.ok().build();
  }

  @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
  @GetMapping("/logs/{id}")
  public ResponseEntity<PageModel<Log>> listAllLogsByOwner(
      @PathVariable("id") Long id, @RequestParam Map<String, String> params) {
    var pr = new PageRequestModel(params);
    var pm = logService.listAllLogsByOwner(id, pr);
    return ResponseEntity.ok(pm);
  }
}
