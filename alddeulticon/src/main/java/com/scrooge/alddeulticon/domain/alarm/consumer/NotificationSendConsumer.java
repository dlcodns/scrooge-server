package com.scrooge.alddeulticon.domain.alarm.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrooge.alddeulticon.domain.alarm.dto.NotificationRequestDto;
import com.scrooge.alddeulticon.domain.alarm.dto.NotificationResponseDto;
import com.scrooge.alddeulticon.domain.alarm.entity.Notification;
import com.scrooge.alddeulticon.domain.alarm.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSendConsumer {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "notification.dispatch", groupId = "notification_group")
    public void send(String message) {
        log.info("전송용 Consumer 수신: {}", message);
        try {
            Notification notification = objectMapper.readValue(message, Notification.class);
            Long receiverId = notification.getReceiverId();

            // WebSocket 전송
            messagingTemplate.convertAndSend(
                    "/topic/notifications/" + receiverId,
                    NotificationResponseDto.from(notification)
            );
        } catch (Exception e) {
            log.error("전송 Consumer 에러", e);
        }
    }
}
