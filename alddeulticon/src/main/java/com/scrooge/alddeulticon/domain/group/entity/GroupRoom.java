package com.scrooge.alddeulticon.domain.group.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_room")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GroupRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;
}