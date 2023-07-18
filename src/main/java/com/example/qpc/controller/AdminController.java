package com.example.qpc.controller;

import com.example.qpc.dto.BlackListDTO;
import com.example.qpc.dto.MemberDTO;
import com.example.qpc.service.AdminService;
import com.example.qpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;

    private final AdminService adminService;

    //회원리스트
    @GetMapping("/memberList")
    public String findMemberList(Model model){

        List<MemberDTO> memberList =memberService.findAll();
        List<BlackListDTO> blackList = adminService.findAll();
        List<String> blackListMemberIds = blackList.stream().map(BlackListDTO::getMemberLoginId).collect(Collectors.toList());

        model.addAttribute("memberList", memberList);
        model.addAttribute("blackListMemberIds", blackListMemberIds);

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
    @GetMapping("memberBlackList")
    public String memberBlackList(Model model){
        List<BlackListDTO> blackListDTOList = adminService.findAll();
        System.out.println("흐옹 blackListDTOList = " + blackListDTOList);
        model.addAttribute("blackList", blackListDTOList);
        return "adminPages/memberBlackList";
    }
    @GetMapping("/addBlack")
    public String addBlack(@RequestParam("memberId") List<String> memberIds) {
        List<String> existingBlackListMembers = new ArrayList<>();

        for (String memberId : memberIds) {
            MemberDTO memberDTO = memberService.findByMemberId(memberId);
            System.out.println("가져왔니 memberDTO = " + memberDTO);

            if (adminService.isBlackListed(memberDTO)) {
                existingBlackListMembers.add(memberDTO.getMemberId());
                continue; // 이미 블랙리스트에 있는 경우 다음 회원으로 넘어감
            }

            if (adminService.addBlack(memberDTO)) {
                // 블랙리스트에 추가되었으므로 계속 진행
            } else {
                // 이미 블랙리스트에 존재하는 경우 등 처리
                return "adminPages/memberDuplicate";
            }
        }

        if (!existingBlackListMembers.isEmpty()) {
            String existingMembers = String.join(", ", existingBlackListMembers);
            System.out.println("이미 블랙리스트에 존재하는 회원: " + existingMembers);
        }

        return "redirect:/admin/memberBlackList";
    }
}
