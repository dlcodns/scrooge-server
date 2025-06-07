package com.scrooge.alddeulticon.domain.preference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceSaveRequest {
    private Long firstKeywordId;
    private Long secondKeywordId;
    private Long thirdKeywordId;
}