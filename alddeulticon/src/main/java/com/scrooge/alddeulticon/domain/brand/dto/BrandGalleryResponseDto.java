package com.scrooge.alddeulticon.domain.brand.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BrandGalleryResponseDto {
    private int brandCount;
    private List<BrandGroupDto> brands;
}
