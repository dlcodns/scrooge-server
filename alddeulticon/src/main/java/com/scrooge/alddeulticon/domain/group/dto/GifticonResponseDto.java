package com.scrooge.alddeulticon.domain.group.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor
public class GifticonResponseDto {
    private String gifticonNumber;
    private String brand;
    private String dueDate;
    private String poster_nickname;
    private String poster_user_id;
    private String imageUrl;
}