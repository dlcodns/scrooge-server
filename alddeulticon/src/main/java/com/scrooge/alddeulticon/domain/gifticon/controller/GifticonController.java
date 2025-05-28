package com.scrooge.alddeulticon.domain.gifticon.controller;

import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonRequestDto;
import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonResponseDto;
import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/gifticon")
@RequiredArgsConstructor
public class GifticonController {

    private final GifticonRepository gifticonRepository;

    // ✅ 저장
    @PostMapping
    public Gifticon save(@RequestBody GifticonRequestDto request) {
        Gifticon gifticon = Gifticon.builder()
                .gifticonNumber(request.getGifticonNumber())
                .whoPost(request.getWhoPost())
                .whichRoom(request.getWhichRoom())
                .dueDate(LocalDate.parse(request.getDueDate()))
                .brand(request.getBrand())
                .productName(request.getProductName())
                .build();
        return gifticonRepository.save(gifticon);
    }

    // ✅ 전체 조회
    @GetMapping
    public List<GifticonResponseDto> getAllGifticons() {
        return gifticonRepository.findAll().stream()
                .map(GifticonResponseDto::new)
                .toList(); // Java 17 이상
    }

    @GetMapping("/{gifticonNumber}")
    public GifticonResponseDto getGifticon(@PathVariable String gifticonNumber) {
        Gifticon gifticon = gifticonRepository.findById(gifticonNumber)
                .orElseThrow(() -> new RuntimeException("기프티콘을 찾을 수 없습니다."));
        return new GifticonResponseDto(gifticon);
    }

}
