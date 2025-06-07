package com.scrooge.alddeulticon.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupResponseDto {
    private Long userId;
    private String nickname;
    private String message;
}
