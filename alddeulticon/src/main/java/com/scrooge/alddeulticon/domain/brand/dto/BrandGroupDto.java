package com.scrooge.alddeulticon.domain.brand.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BrandGroupDto {
    private String brand;
    private List<BrandGifticonDto> gifticons;
    private List<String> gifticonNumbers;
}
