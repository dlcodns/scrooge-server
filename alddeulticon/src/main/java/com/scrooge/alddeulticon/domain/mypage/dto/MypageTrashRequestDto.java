package com.scrooge.alddeulticon.domain.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MypageTrashRequestDto {

    private String gifticonId;  // 올바른 이름으로 수정
    private String whoUse;    // optional (누가 사용했는지)
}
