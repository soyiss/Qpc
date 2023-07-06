package com.example.qpc.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ChatMessageDTO {
    private Long id;
    private String messageContent;	// 메세지 내용
    private Date messageRegDate;	// 메세지 보낸 시간
    private String userUuid;		// 보낸이
    private int unReadCount;		// 메세지 확인 여부
}
