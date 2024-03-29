package com.college.hotlittlepigs.sensors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorsRepository extends JpaRepository<Sensors, Long> {

  Page<Sensors> findAllByBoxId(Long id, Pageable pageable);

  Page<Sensors> findAllByStatus(Boolean status, Pageable pageable);
}
