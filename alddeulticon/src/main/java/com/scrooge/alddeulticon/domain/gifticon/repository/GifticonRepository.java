package com.scrooge.alddeulticon.domain.gifticon.repository;

import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GifticonRepository extends JpaRepository<Gifticon, String> {
    // PK가 gifticonNumber(String)이므로 <Gifticon, String>
}
