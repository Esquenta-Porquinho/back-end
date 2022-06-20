package com.college.hotlittlepigs.log_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogControllersRepository extends JpaRepository<LogController, Long> {

  Page<LogController> findAllByControllerId(Long id, Pageable pageable);
}
