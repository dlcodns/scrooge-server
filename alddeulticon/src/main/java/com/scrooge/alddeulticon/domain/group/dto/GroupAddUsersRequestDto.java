package com.scrooge.alddeulticon.domain.group.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GroupAddUsersRequestDto {
    private List<String> userIds;
}
