package com.example.qpc.dto;

import com.example.qpc.entity.GameFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameFileDTO {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private Long gameId;

    public static GameFileDTO toDTO(GameFileEntity gameFileEntity) {
        GameFileDTO gameFileDTO = new GameFileDTO();
        gameFileDTO.setId(gameFileEntity.getId());
        gameFileDTO.setOriginalFileName(gameFileEntity.getOriginalFileName());
        gameFileDTO.setStoredFileName(gameFileEntity.getStoredFileName());
        gameFileDTO.setGameId(gameFileEntity.getId());
        return gameFileDTO;
    }
}
