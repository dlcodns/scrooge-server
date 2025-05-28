package com.scrooge.alddeulticon.domain.gifticon.service;

import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonRequestDto;
import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GifticonService {

    private final GifticonRepository gifticonRepository;

    public void save(GifticonRequestDto dto) {
        Gifticon gifticon = Gifticon.builder()
                .gifticonNumber(dto.getGifticonNumber()) // ✅ 문자열 그대로 사용
                .whoPost(dto.getWhoPost())
                .whichRoom(dto.getWhichRoom() == null ? "defaultRoom" : dto.getWhichRoom())
                .dueDate(LocalDate.parse(dto.getDueDate()))
                .brand(dto.getBrand())
                .productName(dto.getProductName() == null ? "" : dto.getProductName())
                .build();

        gifticonRepository.save(gifticon);
    }
}
