package com.example.qpc.controller;

import com.example.qpc.dto.MemberDTO;
import com.example.qpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;

    @GetMapping("/adminMain")
    public String adminMain() {
        return "/adminPages/adminMain";
    }

    //회원리스트
    @GetMapping("/memberList")
    public String findMemberList(Model model){

        List<MemberDTO> memberList =memberService.findAll();
        model.addAttribute("memberList", memberList);
        System.out.println("memberDTOList = " + memberList);
        return "/adminPages/memberList";
    }

    //회원상세조회
    @GetMapping("/memberDetail/{memberId}")
    public String memberDetail(@PathVariable String memberId, Model model){
        System.out.println("memberId = " + memberId);
        MemberDTO memberDTO = memberService.findByMemberId(memberId);
        model.addAttribute("member", memberDTO);
        return "/adminPages/memberDetail";
    }

    //회원수정
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody MemberDTO memberDTO) {
        memberService.memberUpdate(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //회원삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void memberDelete(@PathVariable Long id) {
        System.out.println("삭제확인 id = " + id);
        memberService.delete(id);
    }

}
