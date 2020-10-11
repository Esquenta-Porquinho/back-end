package com.college.hotlittlepigs.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControllersRepository extends JpaRepository<Controllers, Long>{
    
    public Page<Controllers> findAllByWork(Boolean work, Pageable pageable);

    public Page<Controllers> findAllByStatus(Boolean status, Pageable pageable);

    public Page<Controllers> findAllByBoxId(Long id, Pageable pageable);
}
