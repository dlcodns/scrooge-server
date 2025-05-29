package com.scrooge.alddeulticon.domain.group.dto;

import lombok.*;
import java.util.List;

@Getter @Setter
public class GroupGifticonAddRequestDto {
    private Long groupId;
    private List<String> gifticonNumbers;
}