package com.scrooge.alddeulticon.controller;

import com.scrooge.alddeulticon.domain.group.dto.*;
import com.scrooge.alddeulticon.domain.group.service.GroupService;
import com.scrooge.alddeulticon.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;
    private final JwtUtil jwtUtil;

    // 1. 그룹 생성
    @PostMapping("/create")
    public void createGroup(@RequestBody GroupCreateRequestDto dto) {
        groupService.createGroup(dto);
    }

    // 2. 사용자 소유 기프티콘 조회 (PK 기반)
    @GetMapping("/{userId}/gifticons")
    public List<GifticonResponseDto> getUserGifticonsById(@PathVariable Long userId) {
        return groupService.getGifticonsByUserIdOrToken(userId, null, false);
    }

    // 3. 로그인한 사용자(JWT) 기프티콘 조회
    @GetMapping("/my-gifticons")
    public List<GifticonResponseDto> getMyGifticons(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        return groupService.getGifticonsByUserIdOrToken(null, token, true);
    }

    // 4. 기존 그룹에 사용자 추가
    @PostMapping("/{groupId}/add-users")
    public void addUsersToGroup(@PathVariable Long groupId,
                                @RequestBody GroupAddUsersRequestDto dto) {
        groupService.addUsersToGroup(groupId, dto.getUserIds());
    }

    // 5. 그룹에 기프티콘 추가
    @PostMapping("/add-gifticons")
    public void addGifticonsToGroup(@RequestBody GroupGifticonAddRequestDto dto) {
        groupService.addGifticonsToGroup(dto);
    }
}
