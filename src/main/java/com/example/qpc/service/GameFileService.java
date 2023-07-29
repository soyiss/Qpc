package com.example.qpc.service;

import com.example.qpc.dto.GameFileDTO;
import com.example.qpc.entity.GameFileEntity;
import com.example.qpc.repository.GameFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameFileService {
    private final GameFileRepository gameFileRepository;

    public GameFileDTO findByGameId(Long id) {
        GameFileEntity gameFileEntity = gameFileRepository.findByGameEntityId(id);
        return GameFileDTO.toDTO(gameFileEntity);
    }
}
