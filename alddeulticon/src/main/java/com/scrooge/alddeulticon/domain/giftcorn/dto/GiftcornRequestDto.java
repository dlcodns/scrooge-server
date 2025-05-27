package com.scrooge.alddeulticon.domain.giftcorn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GiftcornRequestDto {
    @NotBlank(message = "게시자 정보는 필수입니다.")
    private String whoPost;

    @NotBlank(message = "방 정보는 필수입니다.")
    private String whichRoom;

    @NotNull(message = "유효기간은 필수입니다.")
    private LocalDate dueDate;

    @NotBlank(message = "브랜드는 필수입니다.")
    @Size(max = 30, message = "브랜드는 30자 이내여야 합니다.")
    private String brand;

    @NotBlank(message = "상품명은 필수입니다.")
    @Size(max = 30, message = "상품명은 30자 이내여야 합니다.")
    private String productName;
}
