package com.scrooge.alddeulticon.domain.gifticon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GifticonRequestDto {
    private String gifticonNumber;
    private String brand;
    private String dueDate;
    private String productName;
    private String whichRoom;
    private String whoPost;

}
