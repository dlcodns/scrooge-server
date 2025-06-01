package com.scrooge.alddeulticon.domain.group.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupGifticonAddRequestDto {
    private Long groupId;
    private List<String> gifticonNumbers;
}
