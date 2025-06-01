package com.scrooge.alddeulticon.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import com.scrooge.alddeulticon.domain.user.enums.RequestStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
