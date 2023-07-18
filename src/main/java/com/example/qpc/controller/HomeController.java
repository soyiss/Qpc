package com.example.qpc.controller;

import com.example.qpc.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(HttpServletRequest request,Model model) {
        // model에 담아서 form 준비
        model.addAttribute("memberDTO", new MemberDTO());
        return "index";
    }

//    @GetMapping("/index")
//    public String indexing(Model model){
//        model.addAttribute("memberDTO", new MemberDTO());
//        return "index";
//    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
