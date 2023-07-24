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

    @Column(length = 50,nullable = false)
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

    public static GameEntity toSaveEntity(GameDTO gameDTO,GameCategoryEntity gameCategoryEntity) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameName(gameDTO.getGameName());
        gameEntity.setGameLink(gameDTO.getGameLink());

        // 게임 카테고리 정보 설정
        gameCategoryEntity.setId(gameDTO.getGameCategoryId());
        gameEntity.setGameCategoryEntity(gameCategoryEntity);

        gameEntity.setFileAttached(0);

        return gameEntity;
    }


    public static GameEntity toSaveEntityWithFile(GameDTO gameDTO,GameCategoryEntity gameCategoryEntity) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameName(gameDTO.getGameName());
        gameEntity.setGameLink(gameDTO.getGameLink());
        gameEntity.setFileAttached(1);
        // 카테고리 설정
        gameEntity.setGameCategoryEntity(gameCategoryEntity);
        // 파일 첨부 정보 설정
        if(gameDTO.getFileAttached() == 1){
            // 파일이 첨부된 경우, 가능한 경우에만 파일 관련 속성을 설정
            if (!gameDTO.getOriginalFileName().isEmpty() && !gameDTO.getStoredFileName().isEmpty()) {
                List<GameFileEntity> gameFileEntities = new ArrayList<>();
                GameFileEntity gameFileEntity = new GameFileEntity();
                gameFileEntity.setOriginalFileName(gameDTO.getOriginalFileName());
                gameFileEntity.setStoredFileName(gameDTO.getStoredFileName());
                gameFileEntities.add(gameFileEntity);
                gameEntity.setGameFileEntityList(gameFileEntities);
            }

        }
        return gameEntity;

    }


}
