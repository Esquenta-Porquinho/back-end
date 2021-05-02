package com.college.hotlittlepigs.user

import com.college.hotlittlepigs.controller.Controller
import com.college.hotlittlepigs.pagination.PageModel
import com.college.hotlittlepigs.pagination.PageRequestModel
import com.college.hotlittlepigs.security.AccessManager
import com.college.hotlittlepigs.user.dto.UserLoginServiceDTO
import com.college.hotlittlepigs.user.dto.UserUpdateRoleDTO
import com.college.hotlittlepigs.user.enums.Role
import com.college.hotlittlepigs.user.exception.UserNotFoundException
import com.college.hotlittlepigs.user.util.toSecureHash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User as SpringUser

@Service
class UserService : UserDetailsService {
    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var accessManager: AccessManager

    fun updateRole(userDto: UserUpdateRoleDTO, id: Long) {
        if (!accessManager.isAdmin) {
            // TODO do we really need to check again?
            val updatableUser = getById(id)
            if (updatableUser.role === Role.ADMIN || userDto.role === Role.ADMIN) throw AccessDeniedException("Access Denied")
        }
        repository.updateRole(id, userDto.role)
    }

    fun save(user: User): User {
        user.password = user.password.toSecureHash()

        // TODO bug here. If an admin tries to update his/her name, will lose the Role ADMIN.
        //  To reproduce,
        //  1. just login as ADMIN,
        //  2. Call update to name, email and password. (Now you are an Aspirant)
        //  3. Login again.
        //  4. Call any ADMIN endpoint and fail
        user.role = Role.ASPIRANT
        return repository.save(user)
    }

    fun update(user: User): User = repository.save(user)

    fun getById(id: Long): User = repository.findById(id).orElseThrow { UserNotFoundException() }

    fun getByEmail(email: String): User = repository.findByEmail(email).orElseThrow { UserNotFoundException() }

    fun listAll(pr: PageRequestModel): PageModel<User> {
        val pageable: Pageable = pr.toSpringPageRequest()
        val page = repository.findAll(pageable)
        return PageModel(page.totalElements.toInt(), page.size, page.totalPages, page.content)
    }

    fun listAllNotAdmin(pr: PageRequestModel): PageModel<User> {
        val pageable: Pageable = pr.toSpringPageRequest()
        val page = repository.findAllByRoleIsNot(Role.ADMIN, pageable)
        return PageModel(page.totalElements.toInt(), page.size, page.totalPages, page.content)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = getByEmail(username)
        val authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
        return SpringUser(user.email, user.password, authorities)
    }

    fun login(userSpring: SpringUser): UserLoginServiceDTO {
        val email = userSpring.username
        val roles = userSpring.authorities.map { it.authority }
        return UserLoginServiceDTO(email, roles)
    }

    fun sendWarnings(controller: Controller) {
        // TODO Send warning to a Whatsapp number?
    }
}