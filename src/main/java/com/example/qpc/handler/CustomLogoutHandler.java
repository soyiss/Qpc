package com.example.qpc.handler;

import com.example.qpc.dto.MemberDTO;
import com.example.qpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final MemberService memberService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String memberId = userDetails.getUsername();
            System.out.println("핸들러 memberId = " + memberId);
            MemberDTO memberDTO = memberService.findByMemberId(memberId);
            System.out.println("우아 핸들러 memberDTO = " + memberDTO);

            if(memberDTO.getMemberId().equals("admin")) {
                System.out.println(" 나 관리자 " );
                new SecurityContextLogoutHandler().logout(request, response, authentication);
            }else{
                System.out.println(" 나 회원 " );
                String id = request.getParameter("id");
                String overTimeStr = request.getParameter("overTime");
                System.out.println("id = " + id);
                System.out.println("overTimeStr = " + overTimeStr);
                if(overTimeStr == null){
                    throw new IllegalArgumentException("overTimeStr이 null입니다. 유효하지 않은 입력입니다.");
                }else{
                    int overTime = Integer.parseInt(overTimeStr);
                    System.out.println("overTime = " + overTime);
                    // memberDTO에 잔여 시간을 설정하고 저장
                    memberDTO.setOverTime(overTime);
                    memberService.save(memberDTO);
                }
            }
        }

        // 로그아웃 처리를 마친 후에 세션을 비우는 기본 로그아웃 핸들러를 호출합니다.
        new SecurityContextLogoutHandler().logout(request, response, authentication);
    }



}
