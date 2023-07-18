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
        gameDTO.setUploadPath("D:/Springboot_img/");  // uploadPath 필드에 값을 설정

        gameDTO.setId(gameEntity.getId());
        gameDTO.setGameName(gameEntity.getGameName());
        gameDTO.setGameLink(gameEntity.getGameLink());

        GameCategoryEntity gameCategoryEntity = gameEntity.getGameCategoryEntity();
        if (gameCategoryEntity != null && gameCategoryEntity.getGameCategoryName() != null) {
            gameDTO.setGameCategoryId(gameCategoryEntity.getId()); // gameCategoryId 필드에 GameCategoryEntity의 id를 설정
            gameDTO.setGameCategoryName(gameCategoryEntity.getGameCategoryName());
        } else {
            gameDTO.setGameCategoryId(null);
            gameDTO.setGameCategoryName(null);
        }

        // 파일 첨부 여부 확인
        if (gameEntity.getFileAttached() == 1) {
            gameDTO.setFileAttached(1);

            gameDTO.setUploadPath("D:/Springboot_img/");  // uploadPath 필드에 값을 설정

            List<GameFileEntity> gameFileEntityList = gameEntity.getGameFileEntityList();
            if (gameFileEntityList != null && !gameFileEntityList.isEmpty()) {
                GameFileEntity gameFileEntity = gameFileEntityList.get(0);

                // Create GameFileDTO to store file names
                GameFileDTO gameFileDTO = new GameFileDTO();
                gameFileDTO.setId(gameFileEntity.getId());  // id 값 설정 추가
                gameFileDTO.setOriginalFileName(gameFileEntity.getOriginalFileName());
                gameFileDTO.setStoredFileName(gameFileEntity.getStoredFileName());
            }
        } else {
            gameDTO.setFileAttached(0);
        }

        return gameDTO;
    }
}
