package com.college.hotlittlepigs.user

import com.college.hotlittlepigs.user.enums.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Transactional(readOnly = false)
    @Modifying
    @Query("UPDATE User SET role=?2 WHERE id=?1")
    fun updateRole(id: Long, role: Role): Int
    fun findByEmail(email: String): Optional<User>
    fun findAllByRoleIsNot(role: Role, pageable: Pageable): Page<User>

    // TODO this is not used?
    fun findAllByRole(role: Role): List<User>
}