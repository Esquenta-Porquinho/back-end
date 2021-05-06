package com.college.hotlittlepigs.user

import com.college.hotlittlepigs.log.Log
import com.college.hotlittlepigs.log.LogService
import com.college.hotlittlepigs.pagination.PageModel
import com.college.hotlittlepigs.pagination.PageRequestModel
import com.college.hotlittlepigs.security.JwtManager
import com.college.hotlittlepigs.user.dto.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import org.springframework.security.core.userdetails.User as SpringUser

@RequestMapping("users")
@RestController
class UserResource {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var logService: LogService

    @Autowired
    private lateinit var jwtManager: JwtManager

    @Autowired
    private lateinit var authManager: AuthenticationManager

    @PostMapping
    fun save(@RequestBody @Valid userDTO: UserSaveDTO): ResponseEntity<User> {
        val user = userDTO.toUser()
        val createdUser = userService.save(user)
        return status(HttpStatus.CREATED).body(createdUser)
    }

    @PreAuthorize("@accessManager.isOwner(#id)")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid userDTO: UserUpdateDTO): ResponseEntity<User> {
        val user = userDTO.toUser()
        user.id = id
        // TODO we should search by id, not set the id
        val updatedUser = userService.update(user)
        return ok(updatedUser)
    }

    @PreAuthorize("@accessManager.isOwner(#id)")
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<User> = ok(userService.getById(id))

    // TODO we should have a userService implementation that verifies the role and calls the right listAll
    //  Something like: A abstract userService, and two implementations: For non-admins and admins
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    fun listAll(@RequestParam params: Map<String, String>): ResponseEntity<PageModel<User>> {
        val pr = PageRequestModel(params)
        val pm = userService.listAll(pr)
        return ok(pm)
    }

    @Secured("ROLE_ADMIN", "ROLE_MANAGER")
    @GetMapping
    fun listAllNotAdmin(@RequestParam params: Map<String, String>): ResponseEntity<PageModel<User>> {
        val pr = PageRequestModel(params)
        return ok(userService.listAllNotAdmin(pr))
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid user: UserLoginDTO): ResponseEntity<UserLoginResponseDTO> {
        val token = UsernamePasswordAuthenticationToken(user.email, user.password)
        val auth = authManager.authenticate(token)
        SecurityContextHolder.getContext().authentication = auth
        val (email, roles) = userService.login(auth.principal as SpringUser)
        return ok(jwtManager.createToken(email, roles))
    }

    @Secured("ROLE_ADMIN", "ROLE_MANAGER")
    @PreAuthorize("@logHandler.saveLog('Update role' + #id)")
    @PatchMapping("/role/{id}")
    fun updateRole(@PathVariable id: Long, @RequestBody @Valid userDTO: UserUpdateRoleDTO): ResponseEntity<Unit> =
        ok(userService.updateRole(userDTO, id))

    @Secured("ROLE_ADMIN", "ROLE_MANAGER")
    @GetMapping("/logs/{id}")
    fun listAllLogsByOwner(
        @PathVariable id: Long,
        @RequestParam params: Map<String, String>,
    ): ResponseEntity<PageModel<Log>> {
        val pr = PageRequestModel(params)
        val pm = logService.listAllLogsByOwner(id, pr)
        return ok(pm)
    }
}
