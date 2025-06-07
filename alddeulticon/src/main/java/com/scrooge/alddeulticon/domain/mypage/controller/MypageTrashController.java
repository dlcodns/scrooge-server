package com.scrooge.alddeulticon.domain.mypage.controller;

import com.scrooge.alddeulticon.domain.mypage.dto.MypageTrashRequestDto;
import com.scrooge.alddeulticon.domain.mypage.dto.MypageTrashResponseDto;
import com.scrooge.alddeulticon.domain.mypage.service.MypageTrashService;
import com.scrooge.alddeulticon.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage/trash")
@RequiredArgsConstructor
public class MypageTrashController {

    private final MypageTrashService mypageTrashService;

    // 내 휴지통 조회
    @GetMapping("/me")
    public ResponseEntity<List<MypageTrashResponseDto>> getMyTrash(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        List<MypageTrashResponseDto> result = mypageTrashService.getAllTrashByUserId(userId);
        return ResponseEntity.ok(result);
    }

    // 특정 유저의 휴지통 조회 (관리자 권한 필요)
    @PreAuthorize("hasRole('ADMIN')") // 선택 사항: 관리자만 접근하도록 제한
    @GetMapping("/{userId}")
    public ResponseEntity<List<MypageTrashResponseDto>> getTrashByUser(@PathVariable Long userId) {
        List<MypageTrashResponseDto> result = mypageTrashService.getAllTrashByUserId(userId);
        return ResponseEntity.ok(result);
    }

    // 휴지통에 기프트콘 추가
    @PostMapping
    public ResponseEntity<MypageTrashResponseDto> addToTrash(
            @RequestBody MypageTrashRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        MypageTrashResponseDto saved = mypageTrashService.addToTrash(
                userId,
                dto.getGifticonId(),
                dto.getWhoUse()
        );
        return ResponseEntity.ok(saved);
    }
}
