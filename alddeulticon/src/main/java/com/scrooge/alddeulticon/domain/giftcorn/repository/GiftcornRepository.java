package com.scrooge.alddeulticon.domain.giftcorn.repository;

import com.scrooge.alddeulticon.domain.giftcorn.entity.Giftcorn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftcornRepository extends JpaRepository<Giftcorn, Long> {
    // 필요시 커스텀 메서드 추가
}
