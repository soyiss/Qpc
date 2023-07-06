package com.example.qpc.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ChatRoomDTO {
    private Long id;
    private String userUuid;	// 유저
}
