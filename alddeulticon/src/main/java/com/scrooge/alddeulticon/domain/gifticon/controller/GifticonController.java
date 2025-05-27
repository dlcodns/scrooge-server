package com.scrooge.alddeulticon.domain.gifticon.controller;

import com.scrooge.alddeulticon.domain.gifticon.dto.GifticonRequestDto;
import com.scrooge.alddeulticon.domain.gifticon.service.GifticonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gifticons")
@RequiredArgsConstructor
public class GifticonController {

    private final GifticonService gifticonService;

    @PostMapping
    public ResponseEntity<?> saveGifticon(@RequestBody GifticonRequestDto dto) {
        gifticonService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
