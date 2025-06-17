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
    @GetMapping("/{userId}/user_gifticons")
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

    // 6. 로그인한 사용자의 그룹방 목록 조회
    @GetMapping("/my-rooms")
    public List<GroupRoomResponseDto> getMyGroupRooms(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        return groupService.getMyGroupRooms(token);
    }

    // 7. 그룹에 속한 기프티콘 목록 조회
    @GetMapping("/{groupId}/group_gifticons")
    public List<GifticonResponseDto> getGifticonsByGroup(
            @PathVariable Long groupId,
            @RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "").trim();
        return groupService.getGifticonsByGroup(groupId, token);
    }

    // 8. 그룹 ID로 그룹 이름 조회
    @GetMapping("/{groupId}/name")
    public GroupNameResponseDto getGroupNameById(
            @PathVariable Long groupId,
            @RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        return groupService.getGroupNameById(groupId, token);
    }


}
