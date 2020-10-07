package com.college.hotlittlepigs.log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    public Page<Log> findAllByOwnerId(Long id, Pageable pageable);

}
