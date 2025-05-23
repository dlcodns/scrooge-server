package com.scrooge.alddeulticon.domain.user.controller;

import com.scrooge.alddeulticon.domain.user.dto.UserLoginRequestDto;
import com.scrooge.alddeulticon.domain.user.dto.UserLoginResponseDto;
import com.scrooge.alddeulticon.domain.user.dto.UserSignupRequestDto;
import com.scrooge.alddeulticon.domain.user.dto.UserSignupResponseDto;
import com.scrooge.alddeulticon.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
