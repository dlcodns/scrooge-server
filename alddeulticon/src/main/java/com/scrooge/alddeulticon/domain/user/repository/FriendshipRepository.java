package com.scrooge.alddeulticon.domain.user.repository;

import com.scrooge.alddeulticon.domain.user.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findAllByUserId(Long userId);

}
