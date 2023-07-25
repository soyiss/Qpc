package com.example.qpc.controller;

import com.example.qpc.dto.GameDTO;
import com.example.qpc.entity.GameCategoryEntity;
import com.example.qpc.entity.GameEntity;
import com.example.qpc.service.GameCategoryService;
import com.example.qpc.service.GameService;
import com.example.qpc.dto.GameDTO;
import com.example.qpc.entity.GameCategoryEntity;
import com.example.qpc.service.GameCategoryService;
import com.example.qpc.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
@Transactional
public class GameController {

    private final GameService gameService;
    private final GameCategoryService gameCategoryService;

    @GetMapping("/save")
    public String saveForm(Model model){
        List<GameCategoryEntity> gameCategories = gameService.getAllCategories();
        model.addAttribute("gameCategories", gameCategories);
        return "/gamePages/GameSave";
    }

    @PostMapping("/save")
    public String saveGame(@ModelAttribute GameDTO gameDTO) throws IOException {
        // 게임 카테고리 이름을 기반으로 카테고리 ID를 가져옵니다.
        Long gameCategoryId = gameCategoryService.findGameCategoryIdByName(gameDTO.getGameCategoryName());
        gameDTO.setGameCategoryId(gameCategoryId);
        gameService.save(gameDTO);
        return "redirect:/admin/adminMain";
    }

    @GetMapping("/list")
    public String gameList(Model model){
        List<GameDTO> gameDTOList = gameService.findAll();
        System.out.println("id1 " + gameDTOList);
        model.addAttribute("gameDTOList",gameDTOList);
        System.out.println("gameDTOList12 = " + gameDTOList);
        return "gamePages/GameList";
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        GameEntity gameEntity = gameService.findById(id);
        GameDTO gameDTO = GameDTO.toDTO(gameEntity);
        System.out.println("gameDTO = " + gameDTO);
        return new ResponseEntity<>(gameDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        System.out.println("id = " + id);
        gameService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
