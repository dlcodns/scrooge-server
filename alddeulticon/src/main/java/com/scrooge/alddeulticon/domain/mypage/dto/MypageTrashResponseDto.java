package com.scrooge.alddeulticon.domain.mypage.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MypageTrashResponseDto {
    private Long id;
    private Long userId;
    private Long giftcornId;
    private String whoUse;
    private LocalDateTime deletedDate;
    private LocalDateTime usedDate;
}
