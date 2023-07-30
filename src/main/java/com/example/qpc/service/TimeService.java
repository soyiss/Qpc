package com.example.qpc.service;

import com.example.qpc.dto.GameCategoryDTO;
import com.example.qpc.dto.MemberDTO;
import com.example.qpc.dto.TimeDTO;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.entity.TimeEntity;
import com.example.qpc.repository.MemberRepository;
import com.example.qpc.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO, TimeDTO timeDTO) {
        MemberEntity member = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        timeRepository.save(TimeEntity.toEntity(timeDTO,member));
    }

    public List<TimeDTO> findAll() {
         List<TimeEntity> timeEntityList  = timeRepository.findAll();
         List<TimeDTO> timeDTOList = new ArrayList<>();
        for (TimeEntity timeEntity: timeEntityList) {
            timeDTOList.add(TimeDTO.toDTO(timeEntity));
        }
        return timeDTOList;

    }




    public TimeDTO findByMemberEntity(MemberDTO member) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(member);
        List<TimeEntity> timeEntityList = timeRepository.findByMemberEntity(memberEntity);

        TimeEntity latestTime = null;
        for (TimeEntity timeEntity : timeEntityList) {
            if (latestTime == null || timeEntity.getCreatedAt().isAfter(latestTime.getCreatedAt())) {
                latestTime = timeEntity;
            }

        }
        return latestTime != null ? TimeDTO.toDTO(latestTime) : null;
    }


}
