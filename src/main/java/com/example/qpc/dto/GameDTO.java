package com.example.qpc.dto;

import com.example.qpc.entity.GameCategoryEntity;
import com.example.qpc.entity.GameEntity;
import com.example.qpc.entity.GameFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private Long id;
    private String gameName;
    private int fileAttached;
    private String gameLink;
    private MultipartFile gameFile;
    private String originalFileName;
    private String storedFileName;
    private GameCategoryEntity gameCategoryEntity;
    private Long gameCategoryId;
    private String gameCategoryName;

    private String uploadPath; // 파일 업로드 경로




    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    // gameCategoryId 필드의 게터와 세터 추가
    public Long getGameCategoryId() {
        return gameCategoryId;
    }

    public void setGameCategoryId(Long gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }

    public static GameDTO toDTO(GameEntity gameEntity) {
        GameDTO gameDTO = new GameDTO();

        gameDTO.setId(gameEntity.getId());
        gameDTO.setGameName(gameEntity.getGameName());
        gameDTO.setGameLink(gameEntity.getGameLink());
        gameDTO.setFileAttached(gameEntity.getFileAttached());

        // 파일 여부에 따른 코드 추가
        if(gameEntity.getFileAttached() ==1){
            gameDTO.setFileAttached(1);
            gameDTO.setOriginalFileName(gameEntity.getGameFileEntityList().get(0).getOriginalFileName());
            gameDTO.setStoredFileName(gameEntity.getGameFileEntityList().get(0).getStoredFileName());
        } else {
            gameDTO.setFileAttached(0);
        }
        // 카테고리 설정

        GameCategoryEntity gameCategoryEntity =gameEntity.getGameCategoryEntity();
        if (gameCategoryEntity != null){
            gameDTO.setGameCategoryName(gameCategoryEntity.getGameCategoryName());
            gameDTO.setGameCategoryId(gameCategoryEntity.getId());
        }


        return gameDTO;
    }
}
