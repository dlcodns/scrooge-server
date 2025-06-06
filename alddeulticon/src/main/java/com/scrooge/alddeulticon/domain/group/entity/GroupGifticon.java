package com.scrooge.alddeulticon.domain.group.entity;

import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_gifticon")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GroupGifticon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupRoom group;

    @ManyToOne
    @JoinColumn(name = "gifticon_number", referencedColumnName = "gifticonNumber", nullable = false)
    private Gifticon gifticon;
}