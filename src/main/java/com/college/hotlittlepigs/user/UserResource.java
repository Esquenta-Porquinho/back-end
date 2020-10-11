package com.college.hotlittlepigs.user;


import java.util.Map;

import javax.validation.Valid;

import com.college.hotlittlepigs.log.Log;
import com.college.hotlittlepigs.log.LogService;
import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.security.JwtManager;
import com.college.hotlittlepigs.user.dto.UserLoginDTO;
import com.college.hotlittlepigs.user.dto.UserLoginResponseDTO;
import com.college.hotlittlepigs.user.dto.UserLoginServiceDTO;
import com.college.hotlittlepigs.user.dto.UserSaveDTO;
import com.college.hotlittlepigs.user.dto.UserUpdateDTO;
import com.college.hotlittlepigs.user.dto.UserUpdateRoleDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "users")
@AllArgsConstructor
public class UserResource {
    private UserService userService;
    private LogService logService;
    private JwtManager jwtManager;
    private AuthenticationManager authManager;

    @PostMapping()
    public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO userDTO){
        User user = userDTO.toUser();
        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("@accessManager.isOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable(name="id") Long id, @RequestBody UserUpdateDTO userDTO){
        User user = userDTO.toUser();
        user.setId(id);
        User updatedUser = userService.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("@accessManager.isOwner(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id){
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }
    
    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/admin")
    public ResponseEntity<PageModel<User>> listAll(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<User> pm = userService.listAll(pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
    @GetMapping
    public ResponseEntity<PageModel<User>> listAllNotAdmin(@RequestParam Map<String, String> params){
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<User> pm = userService.listAllNotAdmin(pr);
        return ResponseEntity.ok(pm);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@Valid @RequestBody UserLoginDTO user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication auth = authManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        UserLoginServiceDTO userLogin = userService.login((org.springframework.security.core.userdetails.User) auth.getPrincipal());

        return ResponseEntity.ok(jwtManager.createToken(userLogin.getEmail(), userLogin.getRoles()));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
    @PatchMapping("/role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable(name="id") Long id, @RequestBody @Valid UserUpdateRoleDTO userDTO){
        userService.updateRole(userDTO, id);
        return ResponseEntity.ok().build();
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
    @GetMapping("/logs/{id}")
    public ResponseEntity<PageModel<Log>> listAllLogsByOwner(
        @PathVariable("id") Long id,
        @RequestParam Map<String, String> params
    ) {
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Log> pm = logService.listAllLogsByOwner(id, pr);
        return ResponseEntity.ok(pm);
    }

}