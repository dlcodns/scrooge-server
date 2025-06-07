package com.scrooge.alddeulticon.domain.alarm.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrooge.alddeulticon.domain.alarm.dto.NotificationRequestDto;
import com.scrooge.alddeulticon.domain.alarm.entity.Notification;
import com.scrooge.alddeulticon.domain.alarm.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSaveConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "notification.request", groupId = "notification_group")
    public void consume(String message) {
        log.info("저장용 Consumer 수신: {}", message);
        try {
            NotificationRequestDto dto = objectMapper.readValue(message, NotificationRequestDto.class);
            Notification entity = Notification.builder()
                    .type(dto.getType())
                    .message(dto.getMessage())
                    .senderId(dto.getSenderId())
                    .receiverId(dto.getReceiverId())
                    .targetId(dto.getTargetId())
                    .isRead(false)
                    .build();
            notificationRepository.save(entity);
        } catch (Exception e) {
            log.error("저장 Consumer 에러", e);
        }
    }
}