package com.scrooge.alddeulticon.domain.giftcorn.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "giftcorn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Giftcorn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giftcornNumber;  // PK

    @Column(nullable = false)
    private String whoPost;       // 누가 올렸는지

    @Column(nullable = false)
    private String whichRoom;     // 어느 방에서 올렸는지

    @Column(nullable = false)
    private LocalDate dueDate;    // 유효기간

    @Column(nullable = false)
    private String brand;         // 브랜드

    @Column(nullable = false)
    private String productName;   // 상품명
}
