package com.example.qpc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "game_file_table")
public class GameFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=100)
    private String originalFileName;
    @Column(length=100)
    private String storedFileName;

    @ManyToOne
    @JoinColumn(name="game_id")
    private GameEntity gameEntity;

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public static GameFileEntity toSaveGameFileEntity(GameEntity savedEntity, String originalFileName, String storedFileName) {
        GameFileEntity gameFileEntity = new GameFileEntity();
        gameFileEntity.setGameEntity(savedEntity);
        gameFileEntity.setOriginalFileName(originalFileName);
        gameFileEntity.setStoredFileName(storedFileName);

        return gameFileEntity;
    }
}
