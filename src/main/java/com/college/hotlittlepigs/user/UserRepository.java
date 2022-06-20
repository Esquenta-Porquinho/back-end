package com.college.hotlittlepigs.user;

import com.college.hotlittlepigs.user.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Transactional(readOnly = false)
  @Modifying
  @Query("UPDATE User SET role=?2 WHERE id=?1")
  int updateRole(Long id, Role role);

  Optional<User> findByEmail(String email);

  Page<User> findAllByRoleIsNot(Role role, Pageable pageable);

  List<User> findAllByRole(Role role);
}
