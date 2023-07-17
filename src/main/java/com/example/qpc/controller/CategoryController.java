package com.example.qpc.controller;

import com.example.qpc.dto.CategoryDTO;
import com.example.qpc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public String Category(){
        return "/categories/productCategoryForm";
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

    @GetMapping("/update/{id}")
    public ResponseEntity categoryUpdateForm(@PathVariable Long id){
        System.out.println("업 id = " + id);
        CategoryDTO categoryDTO = categoryService.findById(id);
        System.out.println("업 categoryDTO = " + categoryDTO);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity categoryUpdate (@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        System.out.println("categoryDTO = " + categoryDTO);
        categoryDTO.setId(id);
        categoryService.update(categoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity categoryDelete(@PathVariable Long id){
        System.out.println("삭제id = " + id);
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
