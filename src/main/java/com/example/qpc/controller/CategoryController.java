package com.example.qpc.controller;

import com.example.qpc.dto.CategoryDTO;
import com.example.qpc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public String Category(){
        return "/categories/categoryForm";
    }

    @PostMapping()
    public ResponseEntity categorySave(@ModelAttribute CategoryDTO categoryDTO) {
        System.out.println("categoryDTO = " + categoryDTO);
        categoryService.save(categoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity CategoryFindAll(){
        List<CategoryDTO> categoryDTO1 = categoryService.findAll();
        return new ResponseEntity<>(categoryDTO1, HttpStatus.OK);
    }


}
