package com.college.hotlittlepigs.parameters;

import com.college.hotlittlepigs.box.Box;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParametersRepository extends JpaRepository<Parameters, Long> {

  Page<Parameters> findAllByBoxId(Long id, Pageable pageable);

  Optional<Parameters> findByBoxIdAndWeeksAndStatusIsTrue(Long boxId, Double weeks);
}
