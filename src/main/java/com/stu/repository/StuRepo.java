package com.stu.repository;

import com.stu.entity.StuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StuRepo extends JpaRepository<StuEntity,Long> {
    List<StuEntity> findByMarksGreaterThanEqual(int marks);
    StuEntity findTopByOrderByMarksDesc();
}

