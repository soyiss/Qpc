package com.example.qpc.controller;

import com.example.qpc.dto.GameDTO;
import com.example.qpc.entity.GameCategoryEntity;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
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
    public String save(@ModelAttribute GameDTO gameDTO) throws IOException {
        // 게임 카테고리 이름을 기반으로 카테고리 ID를 가져옵니다.
        Long gameCategoryId = gameCategoryService.findGameCategoryIdByName(gameDTO.getGameCategoryName());
        // 게임 카테고리 ID를 설정합니다.
        gameDTO.setGameCategoryId(gameCategoryId);
        // 게임 저장 로직 수행
        gameService.save(gameDTO);
        System.out.println("gameCategoryId = " + gameCategoryId);
        return "redirect:/";
    }




}
