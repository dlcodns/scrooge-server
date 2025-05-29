package com.scrooge.alddeulticon.domain.group.service;

import com.scrooge.alddeulticon.domain.group.dto.*;
import com.scrooge.alddeulticon.domain.group.entity.*;
import com.scrooge.alddeulticon.domain.group.repository.*;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRoomRepository groupRoomRepository;
    private final GroupUserRepository groupUserRepository;
    private final GroupGifticonRepository groupGifticonRepository;
    private final UserRepository userRepository;
    private final GifticonRepository gifticonRepository;

    public void createGroup(GroupCreateRequestDto dto) {
        GroupRoom group = groupRoomRepository.save(new GroupRoom(null, dto.getRoomName()));
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        groupUserRepository.save(new GroupUser(null, group, user));
    }

    public List<GifticonResponseDto> getUserGifticons(Long userId) {
        return gifticonRepository.findByWhoPost(userId).stream()
                .map(g -> new GifticonResponseDto(
                        g.getGifticonNumber(),
                        g.getBrand(),
                        g.getDueDate(),
                        g.getProductName(),
                        g.getWhoPost().toString()
                ))
                .collect(Collectors.toList());
    }

    public void addGifticonsToGroup(GroupGifticonAddRequestDto dto) {
        GroupRoom group = groupRoomRepository.findById(dto.getGroupId()).orElseThrow();
        for (String gifticonNumber : dto.getGifticonNumbers()) {
            Gifticon gifticon = gifticonRepository.findByGifticonNumber(gifticonNumber).orElseThrow();
            groupGifticonRepository.save(new GroupGifticon(null, group, gifticon));
        }
    }
}