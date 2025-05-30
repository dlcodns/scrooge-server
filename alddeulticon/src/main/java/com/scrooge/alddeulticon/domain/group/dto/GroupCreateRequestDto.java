package com.scrooge.alddeulticon.domain.group.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupCreateRequestDto {
    private String roomName;
    private List<String> memberIds; // 👈 멤버 userId 리스트
}
