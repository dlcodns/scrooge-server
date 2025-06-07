package com.scrooge.alddeulticon.domain.group.entity;

import jakarta.persistence.*;
import lombok.*;

import com.scrooge.alddeulticon.domain.group.entity.GroupRoom;
import com.scrooge.alddeulticon.domain.user.entity.User;

@Entity
@Table(name = "group_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupRoom group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String nickName;
}
