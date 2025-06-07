package com.scrooge.alddeulticon.domain.mypage.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageTrashResponseDto {
    private Long id;                  // 휴지통 항목 ID
    private String gifticonId;         // 기프트콘 ID
    private String gifticonName;     // 기프트콘 이름 (optional)
    private String whoUse;           // 누가 사용했는지
    private LocalDateTime deletedDate;
    private LocalDateTime usedDate;
}
