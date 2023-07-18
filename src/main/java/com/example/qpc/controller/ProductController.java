package com.example.qpc.controller;

import com.example.qpc.dto.ProductDTO;
import com.example.qpc.entity.CategoryEntity;
import com.example.qpc.service.CategoryService;
import com.example.qpc.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    // 상품등록 화면 이동
    @GetMapping("/save")
    public String saveForm(Model model) {
        List<CategoryEntity> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/productPages/ProductSave";
    }
    // 상품등록처리
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute ProductDTO productDTO) throws IOException {
        // 카테고리 이름을 기반으로 카테고리 ID를 가져옵니다.
        Long categoryId = categoryService.findCategoryIdByName(productDTO.getCategoryName());
        productDTO.setCategoryId(categoryId);

        // 상품을 저장합니다.
        productService.save(productDTO);

        // 상품 등록 후에 홈 화면이나 다른 페이지로 리다이렉트합니다.
        return "redirect:/";
    }


}
