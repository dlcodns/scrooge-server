package com.scrooge.alddeulticon.domain.gifticon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gifticon {

    @Id
    @Column(nullable = false, unique = true)
    private String gifticonNumber; // 🔑 PK

    @Column(nullable = false)
    private String whoPost;

    @Column(nullable = false)
    private String whichRoom;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String productName;
}
