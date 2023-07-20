package com.example.qpc.dto;

import com.example.qpc.entity.GameCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameCategoryDTO {

    private Long id;
    private String gameCategoryName;

    public static GameCategoryDTO toDTO(GameCategoryEntity gameCategoryEntity) {
        GameCategoryDTO gameCategoryDTO = new GameCategoryDTO();
        gameCategoryDTO.setId(gameCategoryEntity.getId());
        gameCategoryDTO.setGameCategoryName(gameCategoryEntity.getGameCategoryName());
        return gameCategoryDTO;
    }


}
