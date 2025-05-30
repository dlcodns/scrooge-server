package com.scrooge.alddeulticon.domain.gifticon.controller;

import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonRequestDto;
import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonResponseDto;
import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import com.scrooge.alddeulticon.domain.gifticon.service.GifticonService;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import com.scrooge.alddeulticon.global.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gifticon")
@RequiredArgsConstructor
public class GifticonController {

    private final GifticonRepository gifticonRepository;
    private final GifticonService gifticonService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> saveGifticon(@RequestBody GifticonRequestDto request,
                                          @RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractUsername(token);

        gifticonService.save(request, userId);
        return ResponseEntity.ok("기프티콘 저장 성공");
    }

    @GetMapping
    public List<GifticonResponseDto> getAllGifticons() {
        return gifticonRepository.findAll().stream()
                .map(GifticonResponseDto::new)
                .toList();
    }

    @GetMapping("/{gifticonNumber}")
    public GifticonResponseDto getGifticon(@PathVariable String gifticonNumber) {
        Gifticon gifticon = gifticonRepository.findById(gifticonNumber)
                .orElseThrow(() -> new RuntimeException("기프티콘을 찾을 수 없습니다."));
        return new GifticonResponseDto(gifticon);
    }
}
