package com.scrooge.alddeulticon.domain.brand.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandGifticonDto {
    private String gifticonNumber;
    private String brand;
    private String dueDate;
    private String posterNickname;
    private String posterUserId;
}
