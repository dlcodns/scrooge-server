package com.scrooge.alddeulticon.domain.alarm.entity;

import com.scrooge.alddeulticon.domain.alarm.type.NotificationType;
import jakarta.persistence.*;
import lombok.*;

/**
 * 사용자에게 전송되는 알림 엔티티
 */
@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type; // 알림 타입

    @Column(nullable = false)
    private String message; // 알림 내용

    private Long senderId;  // 보낸 사람 ID
    private Long receiverId;// 받는 사람 ID
    private Long targetId;  // 관련 친구 ID 또는 방 ID

    @Column(nullable = false)
    private Boolean isRead = false;

}
