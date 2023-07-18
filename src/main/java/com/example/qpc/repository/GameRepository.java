package com.example.qpc.repository;

import com.example.qpc.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity,Long> {
}
