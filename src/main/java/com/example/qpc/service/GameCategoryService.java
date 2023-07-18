package com.example.qpc.service;
import com.example.qpc.dto.GameCategoryDTO;
import com.example.qpc.entity.GameCategoryEntity;
import com.example.qpc.repository.GameCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class GameCategoryService {

    private final GameCategoryRepository gameCategoryRepository;

    public void save(GameCategoryDTO gameCategoryDTO) {
        GameCategoryEntity gameCategoryEntity = GameCategoryEntity.toSaveEntity(gameCategoryDTO);
        gameCategoryRepository.save(gameCategoryEntity);


    }

    public void delete(Long id) {
        gameCategoryRepository.deleteById(id);
    }


    public List<GameCategoryDTO> findAll() {
        List<GameCategoryEntity> gameCategoryEntityList = gameCategoryRepository.findAll();
        List<GameCategoryDTO> gameCategoryDTOList = new ArrayList<>();
        for (GameCategoryEntity gameCategoryEntity : gameCategoryEntityList) {
            gameCategoryDTOList.add(GameCategoryDTO.toDTO(gameCategoryEntity));
        }
        return gameCategoryDTOList;

    }

    public GameCategoryDTO findById(Long id) {
        GameCategoryEntity gameCategoryEntity = gameCategoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        GameCategoryDTO gameCategoryDTO = GameCategoryDTO.toDTO(gameCategoryEntity);
        return gameCategoryDTO;
    }

    public void update(GameCategoryDTO gameCategoryDTO) {
        GameCategoryEntity gameCategoryEntity = GameCategoryEntity.toUpdateEntity(gameCategoryDTO);
        gameCategoryRepository.save(gameCategoryEntity);
    }

    public Long findGameCategoryIdByName(String gameCategoryName) {
        GameCategoryEntity gameCategoryEntity = gameCategoryRepository.findGameCategoryIdByGameCategoryName(gameCategoryName);
        if (gameCategoryEntity != null) {
            return gameCategoryEntity.getId();
        } else {
            // 해당 이름의 카테고리가 존재하지 않는 경우 처리
            throw new NoSuchElementException("이름이 " + gameCategoryName + "인 카테고리를 찾을 수 없습니다.");
        }
    }
}
