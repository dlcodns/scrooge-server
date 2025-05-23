package com.scrooge.alddeulticon.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId; // 로그인용 아이디

    @Column(nullable = false)
    private String password;

    private String region;

    @Column(nullable = false)
    private String nickname;

    private String phone;
    private String email;
}
