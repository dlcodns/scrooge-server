package com.scrooge.alddeulticon.domain.user.controller;

import com.scrooge.alddeulticon.domain.user.dto.FriendRequestDecisionDto;
import com.scrooge.alddeulticon.domain.user.dto.FriendRequestDto;
import com.scrooge.alddeulticon.domain.user.dto.UserFriendDto;
import com.scrooge.alddeulticon.domain.user.service.FriendService;
import com.scrooge.alddeulticon.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/request")
    public ResponseEntity<String> requestFriend(@RequestBody FriendRequestDto dto,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        friendService.sendFriendRequest(userDetails.getId(), dto);
        return ResponseEntity.ok("친구 요청이 전송되었습니다.");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptRequest(@RequestBody FriendRequestDecisionDto dto,
                                                @AuthenticationPrincipal CustomUserDetails user) {
        friendService.acceptFriendRequest(user.getId(), dto);
        return ResponseEntity.ok("친구 요청을 수락했습니다.");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectRequest(@RequestBody FriendRequestDecisionDto dto,
                                                @AuthenticationPrincipal CustomUserDetails user) {
        friendService.rejectFriendRequest(user.getId(), dto);
        return ResponseEntity.ok("친구 요청을 거절했습니다.");
    }

    @GetMapping
    public ResponseEntity<List<UserFriendDto>> getMyFriends(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(friendService.getMyFriends(user.getId()));
    }

}
