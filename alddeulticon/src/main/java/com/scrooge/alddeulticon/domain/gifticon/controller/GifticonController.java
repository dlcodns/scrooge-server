package com.scrooge.alddeulticon.domain.gifticon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonRequestDto;
import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonResponseDto;
import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import com.scrooge.alddeulticon.domain.gifticon.service.GifticonService;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import com.scrooge.alddeulticon.global.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gifticon")
@RequiredArgsConstructor
public class GifticonController {

    private final GifticonRepository gifticonRepository;
    private final GifticonService gifticonService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveGifticonWithImage(
            @RequestPart("data") String data,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String tokenHeader) {

        String token = tokenHeader.replace("Bearer ", "");
        String userId = jwtUtil.extractUsername(token);

        // 1. JSON 문자열을 DTO로 파싱
        GifticonRequestDto request;
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.readValue(data, GifticonRequestDto.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("JSON 파싱 실패");
        }

        // 2. 이미지 저장
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();  // uploads 디렉토리 없으면 생성
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + filename);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("이미지 저장 실패");
        }

        // 3. 이미지 URL 설정
        String imageUrl = "/static/" + filename;
        request.setImageUrl(imageUrl);

        // 4. 저장
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
