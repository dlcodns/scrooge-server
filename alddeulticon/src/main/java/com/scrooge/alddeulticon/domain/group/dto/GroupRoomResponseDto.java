package com.scrooge.alddeulticon.domain.group.dto;

public class GroupRoomResponseDto {
    private Long id;
    private String roomName;

    public GroupRoomResponseDto(Long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public Long getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }
}
