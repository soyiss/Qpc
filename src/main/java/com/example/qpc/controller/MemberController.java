package com.example.qpc.controller;


import com.example.qpc.config.DuplicateMemberException;
import com.example.qpc.dto.MemberDTO;
import com.example.qpc.entity.BlackListEntity;
import com.example.qpc.entity.RoleEntity;
import com.example.qpc.repository.AdminRepository;
import com.example.qpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AdminRepository adminRepository;

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
            if (memberDTO.getFormRole().equals("member")) {
                MemberDTO member = MemberDTO.createMember(memberDTO, passwordEncoder);
                member.setRole(RoleEntity.MEMBER);
                memberService.saveMember(member);
                System.out.println(2);
            } else if (memberDTO.getFormRole().equals("admin")) {
                MemberDTO checkAdmin = memberService.findByRole();
                if (checkAdmin == null) {
                    MemberDTO member = MemberDTO.createMember(memberDTO, passwordEncoder);
                    member.setRole(RoleEntity.ADMIN);
                    memberService.saveMember(member);
                    System.out.println(3);
                } else if (checkAdmin != null) {
                    model.addAttribute("errorMessage", "관리자가 이미 등록되어 있습니다");
                    System.out.println(5);
                    return "/index";
                }
            }
        } catch (DuplicateMemberException e) {
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println(6);
            return "/index";
        }
        System.out.println(7);
        return "redirect:/";
    }

    // 로그인 창으로 이동
//    @GetMapping("/login")
//    public String memberLoginForm(Model model) {
//        model.addAttribute("memberDTO", new MemberDTO());
//        return "/memberLogin";
//    }

    // 로그인 에러
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "/index";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String memberLogin(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
        try {
            MemberDTO member = memberService.findByMemberId(memberDTO.getMemberId());
            if (member == null) {
                model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인하세요.");
                return "/index";
            }
            // 블랙리스트 검사
            BlackListEntity blackListEntity = adminRepository.findByMemberLoginId(member.getMemberId());
            if (blackListEntity != null) {
                model.addAttribute("loginErrorMsg", "이 회원은 블랙리스트에 있으므로 로그인 할 수 없습니다.");
                return "/index";
            }

            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    memberDTO.getMemberId(), memberDTO.getMemberPassword());

            // 인증 매니저를 사용하여 인증 수행
            Authentication authentication = authenticationManager.authenticate(authToken);

            // SecurityContextHolder에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증 성공 시 처리
            if (member.getRole().equals(RoleEntity.MEMBER)) {
                // 인증이 성공하면 로그인 처리
                return "/memberPages/memberMain";
            } else if (member.getRole().equals(RoleEntity.ADMIN)) {
                return "redirect:/admin/adminMain";
            }

        } catch (AuthenticationException e) {
            // 인증 실패 시 에러 처리
            System.out.println(e);
            model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인하세요.");
            return "/index";
        }
        return "/index";
    }



    // 아이디 찾기
    @GetMapping("/findById/email_check")
    public ResponseEntity memberEmailCheck(@RequestParam String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
        MemberDTO member = memberService.findByMemberEmail(memberEmail);
        System.out.println("member = " + member);
        if (member == null) {
            return new ResponseEntity<>("이메일을 가진 유저가 존재하지 않습니다",HttpStatus.CONFLICT);
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




    @GetMapping("/memberlogintest")
    public String memberlogintest(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "/memberLogin";
    }

    @GetMapping("/membersignuptest")
    public String membersignuptest(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "/index";
    }

    @GetMapping("/memberFood")
    public String memberFood() {
        return "/memberPages/memberFood";
    }
    @GetMapping("/memberMain")
    public String memberMain(){return "/memberPages/memberMain";}

    @GetMapping("/myPage")
    public String memberMyPage(){
        return "/memberPages/memberMyPage";
    }



}






