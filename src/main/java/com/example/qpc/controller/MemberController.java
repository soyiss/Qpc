package com.example.qpc.controller;


import com.example.qpc.dto.MemberDTO;
import com.example.qpc.entity.RoleEntity;
import com.example.qpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/save")
    public String memberSave(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
        // @Valid = 객체의 유효성검사
        System.out.println("memberDTO = " + memberDTO);
        if (bindingResult.hasErrors()) {
            System.out.println(1);
            return "/index";
        }
        try {
            MemberDTO member = MemberDTO.createMember(memberDTO, passwordEncoder);
            member.setRole(RoleEntity.ROLE_MEMBER);
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

    @GetMapping("/login")
    public String memberLoginForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "/memberLogin";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "/memberLogin";
    }

    @PostMapping("/login")
    public String memberLogin(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
        System.out.println("memberDTO = " + memberDTO);
        MemberDTO member = memberService.findByMemberId(memberDTO.getMemberId());
        if (member == null) { // 아이디가 맞지 않을때
            model.addAttribute("loginErrorMsg", "아이디를 확인하세요");
            return "/memberLogin";
        }
        if (passwordEncoder.matches(memberDTO.getMemberPassword(), member.getMemberPassword())) {
            // 로그인 성공
            session.setAttribute("loginId", member.getMemberId());
            return "/memberPages/memberMain";
        } else {
            // 비밀번호가 맞지 않을때
            model.addAttribute("loginErrorMsg", "비밀번호를 확인하세요");
            return "/memberLogin";
        }
    }
}






