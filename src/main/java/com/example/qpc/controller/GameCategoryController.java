package com.example.qpc.controller;

import com.example.qpc.dto.CategoryDTO;
import com.example.qpc.dto.GameCategoryDTO;
import com.example.qpc.service.GameCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/GameCategory")
public class GameCategoryController {
    private final GameCategoryService gameCategoryService;

    @GetMapping("/game")
    public String GameCategory(Model model){
        List<GameCategoryDTO> categoryDTOList = gameCategoryService.findAll();
        model.addAttribute("categoryList",categoryDTOList);
        return "/categories/gameCategoryForm";
    }

    @PostMapping()
    public ResponseEntity categorySave(@ModelAttribute GameCategoryDTO gameCategoryDTO) {
        System.out.println("gameCategoryDTO = " + gameCategoryDTO);
        gameCategoryService.save(gameCategoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity CategoryFindAll(){
        List<GameCategoryDTO> categoryDTO1 = gameCategoryService.findAll();
        System.out.println("게임 categoryDTO1 = " + categoryDTO1);
        return new ResponseEntity<>(categoryDTO1, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity categoryDelete(@PathVariable Long id){
        System.out.println("삭제id = " + id);
        gameCategoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity categoryUpdateForm(@PathVariable Long id){
        System.out.println("업 id = " + id);
        GameCategoryDTO gameCategoryDTO = gameCategoryService.findById(id);
        System.out.println("업 gameCategoryDTO = " + gameCategoryDTO);
        return new ResponseEntity<>(gameCategoryDTO, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity categoryUpdate (@PathVariable("id") Long id, @RequestBody GameCategoryDTO gameCategoryDTO){
        System.out.println("gameCategoryDTO = " + gameCategoryDTO);
        gameCategoryDTO.setId(id);
        gameCategoryService.update(gameCategoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
