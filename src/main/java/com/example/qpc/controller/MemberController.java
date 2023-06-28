package com.example.qpc.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
//    private final MemberService memberService;
//    private final UserService userService;
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
