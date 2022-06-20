package com.college.hotlittlepigs.box;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {

  Optional<Box> findBoxByNumberAndStatus(int number, Boolean status);
}
