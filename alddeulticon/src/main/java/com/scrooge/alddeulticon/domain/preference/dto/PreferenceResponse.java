package com.scrooge.alddeulticon.domain.preference.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreferenceResponse {
    private String first;
    private String second;
    private String third;
    private String nickname; // 친구 프로필용
}