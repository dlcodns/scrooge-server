package com.scrooge.alddeulticon.domain.alarm.controller;

import com.scrooge.alddeulticon.domain.alarm.dto.NotificationRequestDto;
import com.scrooge.alddeulticon.domain.alarm.dto.NotificationResponseDto;
import com.scrooge.alddeulticon.domain.alarm.service.NotificationService;
import com.scrooge.alddeulticon.domain.alarm.producer.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//알림 관련 HTTP 요청을 처리하는 컨트롤러
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationProducer notificationProducer;

    //알림 생성 요청 → Kafka 전송
    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequestDto notificationDto) {
        notificationService.sendNotification(notificationDto);
        return ResponseEntity.ok("Kafka에 알림 전송 완료!");
    }

    //특정 사용자의 알림 전체 조회
    @GetMapping("/{receiverId}")
    public ResponseEntity<List<NotificationResponseDto>> getUserNotifications(@PathVariable Long receiverId) {
        return ResponseEntity.ok(notificationService.getNotificationsByReceiverId(receiverId));
    }

    //특정 사용자의 읽지 않은 알림 개수 조회
    @GetMapping("/{receiverId}/unread-count")
    public ResponseEntity<Long> countUnread(@PathVariable Long receiverId) {
        return ResponseEntity.ok(notificationService.countUnread(receiverId));
    }

    @PostMapping("/test")
    public ResponseEntity<String> testSend(@RequestBody NotificationRequestDto requestDto) {
        notificationProducer.send(requestDto); // 혹은 service.send()
        return ResponseEntity.ok("sent");
    }

}
