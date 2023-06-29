package com.example.qpc.controller;


import com.example.qpc.dto.MemberDTO;
import com.example.qpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/save")
    public String memberSave(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
        // @Valid = 객체의 유효성검사
        System.out.println("memberDTO = " + memberDTO);
        if(bindingResult.hasErrors()) {
            System.out.println(1);
            return "/index";
        }
        try {
            MemberDTO member = MemberDTO.createMember(memberDTO,passwordEncoder);
            memberService.saveMember(member);
            System.out.println(2);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println(3);
            return "/index";
        }
        System.out.println(4);
        return "redirect:/";
    }
//
//    @PostMapping("/save")
//    public String memberSave(@ModelAttribute MemberDTO memberDTO) {
//        UserDTO userDTO = userService.save();
//        memberDTO.setUserId(userDTO.getId());
//        // 회원이 작성한 비번을 시큐리티로 해서 변환해줌
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        memberDTO.setSalt(passwordEncoder.encode(memberDTO.getMemberPassword()));
//
//        System.out.println("memberDTO = " + memberDTO);
//
//        memberService.save(memberDTO,userDTO);
//        return "index";
//    }
}
