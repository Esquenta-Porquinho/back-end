package com.college.hotlittlepigs.security

import com.college.hotlittlepigs.user.User
import com.college.hotlittlepigs.user.UserService
import com.college.hotlittlepigs.user.enums.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AccessManager {
    @Autowired
    private lateinit var userService: UserService

    fun isOwner(id: Long): Boolean = currentUser.id == id

    val isAdmin: Boolean
        get() = currentUser.role === Role.ADMIN

    val currentUser: User
        get() = currentUser()

    private fun currentUser(): User {
        val email = SecurityContextHolder.getContext().authentication.principal as String
        return userService.getByEmail(email)
    }

}