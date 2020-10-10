package com.college.hotlittlepigs.box;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long>{
    
    @Query("SELECT U FROM box U WHERE number=?1 and status=?2")
    public Optional<Box> findByNumberByStatus(int number, Boolean status);
    
}
