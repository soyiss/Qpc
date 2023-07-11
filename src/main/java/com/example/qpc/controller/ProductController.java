package com.example.qpc.controller;

import com.example.qpc.dto.ProductDTO;
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

    @GetMapping("/save")
    public String saveForm(){
        return "/productPages/ProductSave";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute ProductDTO productDTO) throws IOException {
        productService.save(productDTO);
        return "redirect:/";
    }


}
