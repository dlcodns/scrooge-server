package com.scrooge.alddeulticon.domain.gifticon.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface GifticonRepository extends JpaRepository<Gifticon, String> {
    List<Gifticon> findByPosterUserId(String posterUserId);

    Optional<Gifticon> findByGifticonNumber(String gifticonNumber);

    List<Gifticon> findAllByGifticonNumberIn(@Param("numbers") Set<String> numbers);


}

