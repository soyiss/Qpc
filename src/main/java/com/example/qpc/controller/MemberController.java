package com.example.qpc.controller;


import com.example.qpc.dto.MemberDTO;
import com.example.qpc.entity.RoleEntity;
import com.example.qpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    // 일반유저 회원가입
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

    // 로그인 창으로 이동
    @GetMapping("/login")
    public String memberLoginForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "/memberLogin";
    }

    // 로그인 에러
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "/memberLogin";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String memberLogin(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model,
                              @AuthenticationPrincipal User user) {
        System.out.println("memberDTO = " + memberDTO);
        MemberDTO member = memberService.findByMemberId(memberDTO.getMemberId());
        if (member == null) { // 아이디가 맞지 않을때
            model.addAttribute("loginErrorMsg", "아이디를 확인하세요");
            return "/memberLogin";
        }
        if (passwordEncoder.matches(memberDTO.getMemberPassword(), member.getMemberPassword())) {
            // 로그인 성공
            System.out.println("member = " + member);
            if (member.getRole() == RoleEntity.ROLE_MEMBER) {
                return "/memberPages/memberMain";
            } else if (member.getRole() == RoleEntity.ROLE_ADMIN) {
                return "/adminPages/adminMain";
            }
        } else {
            // 비밀번호가 맞지 않을때
            model.addAttribute("loginErrorMsg", "비밀번호를 확인하세요");
            return "/memberLogin";
        }
        return "redirect:/member/login/error";
    }

    // 아이디 찾기
    @PostMapping("/findById/email_check")
    public ResponseEntity memberEmailCheck(@RequestBody MemberDTO memberDTO) {
        MemberDTO member = memberService.findByMemberEmail(memberDTO.getMemberEmail());
        if (member == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(member.getMemberId(), HttpStatus.OK);
        }
    }

    // 비밀번호 찾기
    @PostMapping("/findById/passwordCheck")
    public ResponseEntity memberPassword(@RequestBody MemberDTO memberDTO) {
        MemberDTO member = memberService.findByMemberEmailAndMemberId(memberDTO.getMemberEmail(), memberDTO.getMemberId());
        if (member == null) {
            // Axios로 실패시 Alert 띄우기
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            // 비밀번호 재설정하는 모달 On
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    // 회원 상세조회 화면 이동
    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("memberDTO", memberDTO);
        return "/memberPages/memberMyPage";
    }

    // 회원 수정처리
    @PutMapping("/member/{id}")
    public String memberUpdate(@PathVariable Long id,
                               @ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        MemberDTO Updatedmember = memberService.memberUpdate(memberDTO);
        System.out.println("Updatedmember = " + Updatedmember);
        return "redirect:/member/mypage" + memberDTO.getId();
    }

    // 회원 삭제처리
    @DeleteMapping("/member/{id}")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/mypage" + id;
    }

    @GetMapping("/adminMain")
    public String adminMain(){
        return "/adminPages/adminMain";
    }


    @GetMapping("/memberFood")
    public String memberFood() {
        return "/memberPages/memberFood";
    }
}






