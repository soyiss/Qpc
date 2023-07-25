package com.example.qpc.repository;

import com.example.qpc.entity.GameFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameFileRepository extends JpaRepository<GameFileEntity,Long> {

    GameFileEntity findByGameEntityId(Long id);

}
