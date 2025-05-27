package com.scrooge.alddeulticon.domain.giftcorn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class GiftcornResponseDto {
    private Long giftcornNumber;
    private String whoPost;
    private String whichRoom;
    private LocalDate dueDate;
    private String brand;
    private String productName;
}
