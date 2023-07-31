package com.example.qpc.controller;

import com.example.qpc.dto.MemberDTO;
import com.example.qpc.dto.SeatDTO;
import com.example.qpc.entity.SeatEntity;
import com.example.qpc.service.MemberService;
import com.example.qpc.service.SeatService;
import com.example.qpc.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;

    private final MemberService memberService;

    private final TimeService timeService;

//    @GetMapping
//    public String getAllSeats(Model model) {
//        List<SeatEntity> seats = seatService.getAllSeats();
//        model.addAttribute("seats", seats);
//        return "/seatPages/seat1";
//    }

    @GetMapping("/seats")
    public String SaveForm() {
        return "/seatPages/seat1";
    }
//
    // 좌석 추가 API
    @PostMapping("/save")
    public ResponseEntity<SeatEntity> addSeat(@RequestBody SeatEntity seatEntity) {
        SeatEntity savedSeatEntity = seatService.addSeat(seatEntity);
        return new ResponseEntity<>(savedSeatEntity, HttpStatus.CREATED);
    }

    // 좌석 선택 시 동작을 처리하는 컨트롤러 메서드
    @PostMapping("/{seatNumber}/select")
    public ResponseEntity<String> selectSeat(@PathVariable int seatNumber) {
        // 좌석 선택에 대한 처리를 수행
        // 예시로 콘솔에 선택된 좌석 번호를 출력
        System.out.println("좌석번호: " + seatNumber);
        return ResponseEntity.ok("Seat selected successfully");
    }

//    @GetMapping("/")
//    public String seat(@ModelAttribute SeatDTO seatDTO, Model model, HttpSession session, HttpServletRequest request) throws Exception {
//
//    }

//    @GetMapping("/seats")
//    public String getSeats(Model model) {
//        List<SeatDTO> seats = seatService.getAllSeats();
//        model.addAttribute("seats", seats);
//        return "/seatPages/seatSave1";
//    }


//    @GetMapping("/seat")
//    public String getSeats(Model model, HttpServletRequest request, @PathVariable Long id) {
//        List<SeatDTO> seats = seatService.getAllSeats();
//        model.addAttribute("seats", seats);
//
//        // 세션에서 memberId 정보를 가져와서 모델에 추가
//        MemberDTO memberDTO = memberService.findById(id);
//        model.addAttribute("member", memberDTO);
//
//        return "/seatPages/seatSave1";
//    }
//
//
//    @PostMapping("/selectSeat")
//    public String selectSeat(Long seatId, Long memberId) {
//        try {
//            seatService.selectSeat(seatId, memberId);
//            return "redirect:/seat";
//        } catch (Exception e) {
//            // 예외 처리 로직 추가 (예: 에러 페이지로 리다이렉트 또는 에러 메시지 표시 등)
//            return "errorPage"; // 에러 페이지의 Thymeleaf 템플릿 이름을 리턴합니다.
//        }
//    }
}
