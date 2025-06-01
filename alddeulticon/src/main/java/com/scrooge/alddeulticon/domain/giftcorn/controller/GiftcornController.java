package com.scrooge.alddeulticon.domain.giftcorn.controller;

import com.scrooge.alddeulticon.domain.giftcorn.dto.GiftcornRequestDto;
import com.scrooge.alddeulticon.domain.giftcorn.dto.GiftcornResponseDto;
import com.scrooge.alddeulticon.domain.giftcorn.service.GiftcornService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; // 추가

import java.util.List;

@RestController
@RequestMapping("/api/giftcorn")
@RequiredArgsConstructor
public class GiftcornController {

    private final GiftcornService giftcornService;

    @PostMapping
    public ResponseEntity<GiftcornResponseDto> createGiftcorn(@RequestBody @Valid GiftcornRequestDto dto) {
        return ResponseEntity.ok(giftcornService.createGiftcorn(dto));
    }

    @GetMapping
    public ResponseEntity<List<GiftcornResponseDto>> getAllGiftcorns() {
        return ResponseEntity.ok(giftcornService.getAllGiftcorns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftcornResponseDto> getGiftcorn(@PathVariable Long id) {
        return ResponseEntity.ok(giftcornService.getGiftcorn(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftcornResponseDto> updateGiftcorn(
            @PathVariable Long id,
            @RequestBody @Valid GiftcornRequestDto dto
    ) {
        return ResponseEntity.ok(giftcornService.updateGiftcorn(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftcorn(@PathVariable Long id) {
        giftcornService.deleteGiftcorn(id);
        return ResponseEntity.noContent().build();
    }
}
