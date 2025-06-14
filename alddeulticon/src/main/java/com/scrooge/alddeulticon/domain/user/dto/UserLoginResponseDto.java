package com.scrooge.alddeulticon.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {
    private Long id;
    private String userId;
    private String nickname;
    private String token;
}