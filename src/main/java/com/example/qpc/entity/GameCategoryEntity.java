package com.example.qpc.entity;

import com.example.qpc.dto.GameCategoryDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "game_category_table")
public class GameCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10,nullable = false)
    private String GameCategoryName;

    @OneToMany(mappedBy = "gameCategoryEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameEntity> gameEntityList = new ArrayList<>();


    public static GameCategoryEntity toSaveEntity(GameCategoryDTO gameCategoryDTO) {
        GameCategoryEntity gameCategoryEntity = new GameCategoryEntity();
        gameCategoryEntity.setGameCategoryName(gameCategoryDTO.getGameCategoryName());
        return gameCategoryEntity;
    }

    public static GameCategoryEntity toUpdateEntity(GameCategoryDTO gameCategoryDTO) {
        GameCategoryEntity gameCategoryEntity = new GameCategoryEntity();
        gameCategoryEntity.setId(gameCategoryDTO.getId());
        gameCategoryEntity.setGameCategoryName(gameCategoryDTO.getGameCategoryName());
        return gameCategoryEntity;
    }

}
