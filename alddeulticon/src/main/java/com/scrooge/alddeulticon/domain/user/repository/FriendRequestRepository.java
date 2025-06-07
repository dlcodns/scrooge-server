package com.scrooge.alddeulticon.domain.user.repository;

import com.scrooge.alddeulticon.domain.user.entity.FriendRequest;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    boolean existsBySenderAndReceiverAndStatus(User sender, User receiver, RequestStatus status);
}
