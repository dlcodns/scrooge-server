package com.scrooge.alddeulticon.domain.gifticon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GifticonRequestDto {
    private String gifticonNumber;
    private String whoPost;
    private String whichRoom;
    private String dueDate; // "2025-06-30" 형식
    private String brand;
    private String productName;
}
