package com.scrooge.alddeulticon.domain.user.controller;

import com.scrooge.alddeulticon.domain.user.dto.*;
import com.scrooge.alddeulticon.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody @Valid UserSignupRequestDto dto) {
        return ResponseEntity.ok(userService.signup(dto));
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @GetMapping("/search")
    public ResponseEntity<UserSearchResponseDto> searchUserById(@RequestParam String userId) {
        return ResponseEntity.ok(userService.searchByUserId(userId));
    }

}
