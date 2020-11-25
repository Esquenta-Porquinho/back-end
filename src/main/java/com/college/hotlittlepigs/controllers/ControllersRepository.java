package com.college.hotlittlepigs.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControllersRepository extends JpaRepository<Controller, Long> {

  Page<Controller> findAllByStatus(Boolean status, Pageable pageable);

  Page<Controller> findAllByBoxId(Long id, Pageable pageable);
}
