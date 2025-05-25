package com.scrooge.alddeulticon.domain.alarm.dto;

import com.scrooge.alddeulticon.domain.alarm.type.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {

    private NotificationType type;     // FRIEND_REQUEST, ROOM_INVITE
    private String message;            // 알림 내용
    private Long senderId;
    private Long receiverId;
    private Long targetId;             // 관련 ID (친구/방)

}
