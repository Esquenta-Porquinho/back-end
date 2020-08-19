package com.college.hotlittlepigs.user;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.college.hotlittlepigs.model.PageModel;
import com.college.hotlittlepigs.model.PageRequestModel;
import com.college.hotlittlepigs.security.JwtManager;
import com.college.hotlittlepigs.user.dto.UserLoginDTO;
import com.college.hotlittlepigs.user.dto.UserLoginResponseDTO;
import com.college.hotlittlepigs.user.dto.UserSaveDTO;
import com.college.hotlittlepigs.user.dto.UserUpdateDTO;
import com.college.hotlittlepigs.user.dto.UserUpdateRoleDTO;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@RestController
@RequestMapping(value = "users")
public class UserResource {
    @Autowired private UserService userService;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtManager jwtManager;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO userDTO){
        User user = userDTO.transformToUser();
        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("@accessManager.isOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable(name="id") Long id, @RequestBody UserUpdateDTO userDTO){
        User user = userDTO.transformToUser();
        user.setId(id);
        User updatedUser = userService.save(user);

        return ResponseEntity.ok(updatedUser);
    }

    @Secured({ "ROLE_ADMIN" })
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
        
        org.springframework.security.core.userdetails.User userSpring = 
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        String email = userSpring.getUsername();
        List<String> roles = userSpring.getAuthorities()
                                        .stream()
                                        .map(authority -> authority.getAuthority())
                                        .collect(Collectors.toList());

        return ResponseEntity.ok(jwtManager.createToken(email, roles));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
    @PatchMapping("/role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable(name="id") Long id, @RequestBody @Valid UserUpdateRoleDTO userDTO){
        User user = new User();
        user.setId(id);
        user.setRole(userDTO.getRole());
        
        userService.updateRole(user);
        return ResponseEntity.ok().build();
    }


}