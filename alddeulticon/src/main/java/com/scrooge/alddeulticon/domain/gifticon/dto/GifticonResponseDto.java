package com.scrooge.alddeulticon.domain.gifticon.dto;

import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import lombok.Getter;

@Getter
public class GifticonResponseDto {

    private final String gifticonNumber;
    private final String posterUserId;
    private final String posterNickname;
    private final String dueDate;
    private final String brand;

    public GifticonResponseDto(Gifticon gifticon) {
        this.gifticonNumber = gifticon.getGifticonNumber();
        this.posterUserId = gifticon.getPosterUserId();
        this.posterNickname = gifticon.getPosterNickname();
        this.dueDate = gifticon.getDueDate() != null
                ? gifticon.getDueDate().toString()
                : null;
        this.brand = gifticon.getBrand();
    }
}
