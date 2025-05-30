package com.scrooge.alddeulticon.domain.gifticon.dto;

import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import lombok.Getter;

@Getter
public class GifticonResponseDto {

    private final String gifticonNumber;
    private final String whoPost;
//    private final String whichRoom;
    private final String dueDate;
    private final String brand;
    private final String productName;

    public GifticonResponseDto(Gifticon gifticon) {
        this.gifticonNumber = gifticon.getGifticonNumber();
        this.whoPost = gifticon.getWhoPost();
//        this.whichRoom = gifticon.getWhichRoom();
        this.dueDate = gifticon.getDueDate() != null
                ? gifticon.getDueDate().toString()
                : null;
        this.brand = gifticon.getBrand();
        this.productName = gifticon.getProductName();
    }
}
