package com.scrooge.alddeulticon.domain.alarm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrooge.alddeulticon.domain.alarm.dto.NotificationRequestDto;
import com.scrooge.alddeulticon.domain.alarm.dto.NotificationResponseDto;
import com.scrooge.alddeulticon.domain.alarm.entity.Notification;
import com.scrooge.alddeulticon.domain.alarm.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationRequestDto dto) {
        try {
            String message = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("notification.request", message);
            log.info("Kafka 알림 전송 성공");
        } catch (Exception e) {
            log.error("Kafka 알림 전송 실패", e);
            throw new RuntimeException("Kafka 전송 실패", e);
        }
    }

    public List<NotificationResponseDto> getNotificationsByReceiverId(Long receiverId) {
        return notificationRepository.findByReceiverId(receiverId).stream()
                .map(NotificationResponseDto::from)
                .collect(Collectors.toList());
    }

    public Long countUnread(Long receiverId) {
        return notificationRepository.countByReceiverIdAndIsReadFalse(receiverId);
    }

    @Transactional
    public void markAsRead(Long id) {
        notificationRepository.findById(id)
                .ifPresent(notification -> notification.setIsRead(true));
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public Optional<Notification> getNotification(Long id) {
        return notificationRepository.findById(id);
    }
}
