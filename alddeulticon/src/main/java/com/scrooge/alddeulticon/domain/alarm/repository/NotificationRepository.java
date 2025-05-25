package com.scrooge.alddeulticon.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scrooge.alddeulticon.domain.alarm.entity.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverId(Long receiverId);
    long countByReceiverIdAndIsReadFalse(Long receiverId);
}
