package com.scrooge.alddeulticon.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserFriendDto {
    private Long id;
    private String nickname;
    private List<String> preferences;
}
