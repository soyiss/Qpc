package com.example.qpc.controller;

import com.example.qpc.dto.ProductDTO;
import com.example.qpc.entity.CategoryEntity;
import com.example.qpc.service.CategoryService;
import com.example.qpc.service.ProductService;
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
@Transactional
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
        productService.save(productDTO);
        return "redirect:/admin/adminMain";
    }

    // 카테고리
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    // 상품리스트 화면 이동
    @GetMapping("/list")
    public String productList(Model model) {
        List<ProductDTO> productDTOList = productService.findAll();
        if (productDTOList != null) {
            System.out.println("productList = " + productDTOList);
        }
        model.addAttribute("productList", productDTOList);
        return "productPages/ProductList";
    }

    // 상품 상세 조회 화면 이동
    @GetMapping("/{id}")
    public ResponseEntity ProductDetail(@PathVariable("id") Long id) {
        ProductDTO productDTO = productService.findByProductId(id);
        System.out.println("productDTO = " + productDTO);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    // 상품 수정 처리
    @PostMapping("/update")
    public String productUpdate(@ModelAttribute ProductDTO productDTO) throws IOException {
        System.out.println("productDTO = " + productDTO);
        Long categoryId = categoryService.findCategoryIdByName(productDTO.getCategoryName());
        productDTO.setCategoryId(categoryId);
        productService.update(productDTO);
        return "redirect:/admin/adminMain";
    }

    // 상품 삭제 처리
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        System.out.println("id2 = " + id);
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
