package com.scrooge.alddeulticon.domain.preference.entity;

import com.scrooge.alddeulticon.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_keyword_id")
    private GifticonKeyword first;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_keyword_id")
    private GifticonKeyword second;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "third_keyword_id")
    private GifticonKeyword third;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}