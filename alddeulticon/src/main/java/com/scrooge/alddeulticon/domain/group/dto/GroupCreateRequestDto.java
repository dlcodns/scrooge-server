package com.scrooge.alddeulticon.domain.group.dto;

import lombok.*;

@Getter @Setter
public class GroupCreateRequestDto {
    private String roomName;
    private Long userId;
}