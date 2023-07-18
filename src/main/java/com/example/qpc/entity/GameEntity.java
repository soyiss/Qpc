package com.example.qpc.entity;

import com.example.qpc.dto.GameDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "game_table")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10,nullable = false)
    private String gameName;

    @Column(length = 500,nullable = false)
    private String gameLink;

    //파일 여부를 판단하는 컬럼
    @Column
    private int fileAttached;

    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameFileEntity> gameFileEntityList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="category_id")
    private GameCategoryEntity gameCategoryEntity;

    public static GameEntity toSaveEntity(GameDTO gameDTO) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameName(gameDTO.getGameName());
        gameEntity.setGameLink(gameDTO.getGameLink());

        // 게임 카테고리 정보 설정
        GameCategoryEntity gameCategoryEntity = new GameCategoryEntity();
        gameCategoryEntity.setId(gameDTO.getGameCategoryId());
        gameEntity.setGameCategoryEntity(gameCategoryEntity);

        gameEntity.setFileAttached(0);

        return gameEntity;
    }


    public static GameEntity toSaveEntityWithFile(GameDTO gameDTO) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameName(gameDTO.getGameName());
        gameEntity.setGameLink(gameDTO.getGameLink());
        gameEntity.setFileAttached(1);
        return gameEntity;

    }


    public GameCategoryEntity getGameCategoryEntity() {
        return gameCategoryEntity;
    }
}
