package com.scrooge.alddeulticon.domain.mypage.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mypage_trash")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageTrash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 휴지통 기본키

    @Column(name = "user_id", nullable = false)
    private Long userId; // 누구의 휴지통인지

    @Column(name = "giftcorn_id", nullable = false)
    private Long giftcornId; // giftcorn의 PK 값 (그냥 숫자만 저장)

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate; // 휴지통에 들어간 시점

    @Column(name = "used_date")
    private LocalDateTime usedDate; // 사용한 시점

    @Column(name = "who_use")
    private String whoUse; // 누가 사용했는지
}
