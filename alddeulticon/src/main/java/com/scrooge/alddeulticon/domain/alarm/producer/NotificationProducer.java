package com.scrooge.alddeulticon.domain.alarm.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrooge.alddeulticon.domain.alarm.dto.NotificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(NotificationRequestDto dto) {
        try {
            String message = objectMapper.writeValueAsString(dto);
            log.info("💬 Kafka 전송 시도 메시지: {}", message);
            kafkaTemplate.send("notification.request", message);
            log.info("✅ Kafka 메시지 전송 성공");
        } catch (Exception e) {
            log.error("❌ Kafka 전송 에러", e);
        }
    }
}