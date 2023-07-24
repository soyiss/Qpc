package com.example.qpc.service;

import com.example.qpc.dto.GameDTO;
import com.example.qpc.entity.GameCategoryEntity;
import com.example.qpc.repository.GameCategoryRepository;
import com.example.qpc.repository.GameFileRepository;
import com.example.qpc.repository.GameRepository;
import com.example.qpc.dto.GameDTO;
import com.example.qpc.entity.GameCategoryEntity;
import com.example.qpc.entity.GameEntity;
import com.example.qpc.entity.GameFileEntity;
import com.example.qpc.repository.GameCategoryRepository;
import com.example.qpc.repository.GameFileRepository;
import com.example.qpc.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final GameRepository gameRepository;
    private final GameFileRepository gameFileRepository;

    private final GameCategoryRepository gameCategoryRepository;


    public Long save(GameDTO gameDTO) throws IOException {
        GameCategoryEntity gameCategoryEntity = gameCategoryRepository.findGameCategoryIdByGameCategoryName(gameDTO.getGameCategoryName());
        if (gameDTO.getGameFile().isEmpty()) {
            // 파일 없음
            GameEntity gameEntity = GameEntity.toSaveEntity(gameDTO,gameCategoryEntity);
            return gameRepository.save(gameEntity).getId();
        } else {
            // 파일 있음
            // 1. Game 에 데이터 저장
            GameEntity gameEntity = GameEntity.toSaveEntityWithFile(gameDTO,gameCategoryEntity);
            GameEntity savedEntity = gameRepository.save(gameEntity);

            // 2. 파일이름 꺼내고, 저장용 이름 만들고 파일 로컬에 저장
            String originalFileName = gameDTO.getGameFile().getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
            String savePath = "D:\\springboot_img\\" + storedFileName;
            gameDTO.getGameFile().transferTo(new File(savePath));

            // 3. GameFileEntity로 변환 후 저장
            GameFileEntity gameFileEntity = GameFileEntity.toSaveGameFileEntity(savedEntity, originalFileName, storedFileName);
            gameFileRepository.save(gameFileEntity);

            return savedEntity.getId();
        }
    }


    public List<GameCategoryEntity> getAllCategories() {
        return gameCategoryRepository.findAll();

    }

    public List<GameDTO> findAll() {
        List<GameEntity> gameEntityList = gameRepository.findAll();
        List<GameDTO> gameDTOList = new ArrayList<>();
        gameEntityList.forEach(gameEntity -> {
            gameDTOList.add(GameDTO.toDTO(gameEntity));
        });
        return gameDTOList;
    }


    public void delete(Long id) {
        gameRepository.deleteById(id);
    }
}
