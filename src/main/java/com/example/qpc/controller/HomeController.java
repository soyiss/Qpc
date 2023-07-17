package com.example.qpc.controller;

import com.example.qpc.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(HttpServletRequest request,Model model) {
        // model에 담아서 form 준비
        model.addAttribute("memberDTO", new MemberDTO());
        return "testIndex";
        // testIndex = 신욱
        // Indextest = 유진
        // index = 나중에 합칠것
    }

    @GetMapping("/index")
    public String indexing(Model model){
        model.addAttribute("memberDTO", new MemberDTO());
        return "index";

    }
}
