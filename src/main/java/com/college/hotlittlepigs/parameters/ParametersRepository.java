package com.college.hotlittlepigs.parameters;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametersRepository extends JpaRepository<Parameters, Long>{
    
    public Page<Parameters> findAllByBoxId(Long id, Pageable pageable);
    
    @Query("SELECT U FROM parameters U where box_id=?1 and weeks=?2 and status=1 ")
    public Optional<Parameters> findActiveByWeekByBox(Long idBox, Double weeks);
}
