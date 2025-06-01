package com.scrooge.alddeulticon.domain.mypage.controller;

import com.scrooge.alddeulticon.domain.mypage.dto.MypageTrashRequestDto;
import com.scrooge.alddeulticon.domain.mypage.dto.MypageTrashResponseDto;
import com.scrooge.alddeulticon.domain.mypage.service.MypageTrashService;
import com.scrooge.alddeulticon.global.security.CustomUserDetails; // 직접 만든 UserDetails 구현체
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage/trash")
@RequiredArgsConstructor
public class MypageTrashController {

    private final MypageTrashService mypageTrashService;

    // 인증된 사용자의 trash 리스트 조회 (userId를 토큰에서 자동 추출)
    @GetMapping("/me")
    public List<MypageTrashResponseDto> getMyTrash(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        return mypageTrashService.getAllTrashByUserId(userId);
    }

    // 특정 userId로 trash 리스트 조회 (관리자나 디버그 용)
    @GetMapping("/{userId}")
    public List<MypageTrashResponseDto> getTrashByUser(@PathVariable Long userId) {
        return mypageTrashService.getAllTrashByUserId(userId);
    }

    // trash 추가 (userId를 토큰에서 추출)
    @PostMapping
    public MypageTrashResponseDto addToTrash(
            @RequestBody MypageTrashRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        return mypageTrashService.addToTrash(
                userId, // 바디에서 받지 않고, 인증 정보에서 추출!
                dto.getGiftcornId(),
                dto.getWhoUse()
        );
    }
}
