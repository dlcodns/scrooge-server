package com.scrooge.alddeulticon.controller;

import com.scrooge.alddeulticon.domain.group.dto.*;
import com.scrooge.alddeulticon.domain.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    // 1. 그룹 생성
    @PostMapping("/create")
    public void createGroup(@RequestBody GroupCreateRequestDto dto) {
        groupService.createGroup(dto);
    }

    // 2. 사용자 소유 기프티콘 조회
    @GetMapping("/{userId}/gifticons")
    public List<GifticonResponseDto> getUserGifticons(@PathVariable Long userId) {
        return groupService.getUserGifticons(userId);
    }

    // 3. 그룹에 기프티콘 추가
    @PostMapping("/{groupId}/gifticons")
    public void addGifticons(@PathVariable Long groupId, @RequestBody GroupGifticonAddRequestDto dto) {
        dto.setGroupId(groupId);
        groupService.addGifticonsToGroup(dto);
    }
}