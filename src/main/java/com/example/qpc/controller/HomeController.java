package com.example.qpc.controller;

import com.example.qpc.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        // model에 담아서 form 준비
        model.addAttribute("memberDTO", new MemberDTO());
        return "index";
    }
}
