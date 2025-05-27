package com.scrooge.alddeulticon.domain.mypage.repository;

import com.scrooge.alddeulticon.domain.mypage.entity.MypageTrash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MypageTrashRepository extends JpaRepository<MypageTrash, Long> {
    List<MypageTrash> findByUserId(Long userId);
}
