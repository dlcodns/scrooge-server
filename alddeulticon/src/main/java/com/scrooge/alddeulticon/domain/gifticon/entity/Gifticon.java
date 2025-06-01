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
    private String gifticonNumber;

    @Column(nullable = false)
    private String posterUserId;

    @Column(nullable = false)
    private String posterNickname;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String brand;
}

