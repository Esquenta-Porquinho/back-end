package com.college.hotlittlepigs.gestation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestationRepository extends JpaRepository<Gestation, Long>{
    
    public Page<Gestation> findAllByMatrixId(Long id, Pageable pageable);

    public Page<Gestation> findAllByBoxId(Long id, Pageable pageable);

}
