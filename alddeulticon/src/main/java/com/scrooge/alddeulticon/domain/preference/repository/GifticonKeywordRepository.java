package com.scrooge.alddeulticon.domain.preference.repository;

import com.scrooge.alddeulticon.domain.preference.entity.GifticonKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GifticonKeywordRepository extends JpaRepository<GifticonKeyword, Long> {
    boolean existsByName(String name);
}