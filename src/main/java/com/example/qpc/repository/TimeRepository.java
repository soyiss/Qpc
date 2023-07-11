package com.example.qpc.repository;

import com.example.qpc.entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<TimeEntity,Long> {
}
