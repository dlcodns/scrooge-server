package com.scrooge.alddeulticon.domain.gifticon.service;

import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonRequestDto;
import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GifticonService {

    private final GifticonRepository gifticonRepository;
    private final UserRepository userRepository;

    public void save(GifticonRequestDto dto, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        Gifticon gifticon = Gifticon.builder()
                .gifticonNumber(dto.getGifticonNumber())
                .posterUserId(user.getUserId())
                .posterNickname(user.getNickname())
                .dueDate(LocalDate.parse(dto.getDueDate()))
                .brand(dto.getBrand())
                .build();

        gifticonRepository.save(gifticon);
    }
}
