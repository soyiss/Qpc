package com.example.qpc.repository;

import com.example.qpc.entity.GameCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCategoryRepository extends JpaRepository<GameCategoryEntity,Long> {

    GameCategoryEntity findGameCategoryIdByGameCategoryName(String gameCategoryName);
}
