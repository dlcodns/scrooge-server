package com.scrooge.alddeulticon.domain.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MypageTrashRequestDto {
    private Long userId;
    private Long giftcornId;      // gifticonId → giftcornId로 맞춤
    private String whoUse;        // optional
    // deletedDate, usedDate 등 필요한 값 있으면 추가
}
