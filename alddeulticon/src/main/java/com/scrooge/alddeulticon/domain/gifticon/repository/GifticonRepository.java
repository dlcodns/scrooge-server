package com.scrooge.alddeulticon.domain.gifticon.repository;
import java.util.List;
import java.util.Optional;

import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GifticonRepository extends JpaRepository<Gifticon, String> {
    List<Gifticon> findByWhoPost(String whoPost); // ← 타입 맞춰서
    Optional<Gifticon> findByGifticonNumber(String gifticonNumber);
}

