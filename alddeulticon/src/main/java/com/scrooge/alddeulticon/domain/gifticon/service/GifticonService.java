package com.scrooge.alddeulticon.domain.gifticon.service;

import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonRequestDto;
import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
                .imageUrl(dto.getImageUrl()) // 🔹 이미지 URL 추가
                .build();

        gifticonRepository.save(gifticon);
    }
    public String saveImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String uploadPath = "uploads/" + fileName;

            File dest = new File(uploadPath);

            System.out.println("이미지 저장 절대 경로: " + dest.getAbsolutePath());
            file.transferTo(dest);

            return "/static/" + fileName; // URL로 접근하기 위한 경로 반환
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패", e);
        }
    }
}
