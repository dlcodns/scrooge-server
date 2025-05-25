package com.scrooge.alddeulticon.domain.alarm.dto;

import com.scrooge.alddeulticon.domain.alarm.entity.Notification;
import com.scrooge.alddeulticon.domain.alarm.type.NotificationType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponseDto {
    private Long id;
    private NotificationType type;
    private String message;
    private Long senderId;
    private Long receiverId;
    private Long targetId;
    private Boolean isRead;

    public static NotificationResponseDto from(Notification n) {
        return NotificationResponseDto.builder()
                .id(n.getId())
                .type(n.getType())
                .message(n.getMessage())
                .senderId(n.getSenderId())
                .receiverId(n.getReceiverId())
                .targetId(n.getTargetId())
                .isRead(n.getIsRead())
                .build();
    }
}