package com.example.qpc.service;

import com.example.qpc.dto.SeatDTO;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.entity.SeatEntity;
import com.example.qpc.repository.MemberRepository;
import com.example.qpc.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    //     좌석 정보 가져오기
//    public List<SeatEntity> getAllSeats() {
//        return seatRepository.findAll();
//    }

    // 좌석 추가 메소드
    public SeatEntity addSeat(SeatEntity seatEntity) {
        // 유효성 검사
        if (seatEntity.getSeatName() == null || seatEntity.getSeatName().isEmpty()) {
            throw new IllegalArgumentException("Seat name cannot be null or empty");
        }
        // 좌석 추가
        SeatEntity savedSeatEntity = seatRepository.save(seatEntity);
        return savedSeatEntity;
    }

    public List<SeatDTO> getAllSeats() {
        List<SeatEntity> seatEntities = seatRepository.findAll();
        return seatEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SeatDTO convertToDTO(SeatEntity seatEntity) {
        return modelMapper.map(seatEntity, SeatDTO.class);
    }

    @Transactional
    public void selectSeat(Long seatId, Long memberId) {
        SeatEntity seatEntity = seatRepository.findById(seatId).orElse(null);
        if (seatEntity != null && !seatEntity.isTaken()) {
            seatEntity.setTaken(true);
            // 여기서 memberId에 해당하는 회원 정보를 가져와서 설정하는 로직을 추가해야 합니다.
            MemberEntity memberEntity = memberRepository.findById(memberId).orElse(null);
            seatEntity.setMemberEntity(memberEntity);
        }
    }
}
