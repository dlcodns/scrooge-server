package com.scrooge.alddeulticon.domain.group.service;

import com.scrooge.alddeulticon.domain.group.dto.*;
import com.scrooge.alddeulticon.domain.group.entity.*;
import com.scrooge.alddeulticon.domain.group.repository.*;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import com.scrooge.alddeulticon.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private static final Logger log = LoggerFactory.getLogger(GroupService.class);

    private final GroupRoomRepository groupRoomRepository;
    private final GroupUserRepository groupUserRepository;
    private final GroupGifticonRepository groupGifticonRepository;
    private final UserRepository userRepository;
    private final GifticonRepository gifticonRepository;
    private final JwtUtil jwtUtil;

    public void createGroup(GroupCreateRequestDto dto) {
        GroupRoom group = groupRoomRepository.save(new GroupRoom(null, dto.getRoomName()));
        for (String userId : dto.getMemberIds()) {
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
            GroupUser groupUser = new GroupUser(null, group, user, user.getNickname());
            groupUserRepository.save(groupUser);
        }
    }

    public void addUsersToGroup(Long groupId, List<String> userIds) {
        GroupRoom group = groupRoomRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + groupId));
        for (String userId : userIds) {
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
            if (!groupUserRepository.existsByGroupAndUser(group, user)) {
                groupUserRepository.save(new GroupUser(null, group, user, user.getNickname()));
            }
        }
    }

    public List<GifticonResponseDto> getGifticonsByUserIdOrToken(Long userId, String token, boolean isTokenBased) {
        String resolvedUserId;
        if (isTokenBased) {
            resolvedUserId = jwtUtil.getUserIdFromToken(token);
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            resolvedUserId = user.getUserId();
        }

        return gifticonRepository.findByPosterUserId(resolvedUserId).stream()
                .map(g -> new GifticonResponseDto(
                        g.getGifticonNumber(),
                        g.getBrand(),
                        g.getDueDate().toString(),
                        g.getPosterNickname(),
                        g.getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    public void addGifticonsToGroup(GroupGifticonAddRequestDto dto) {
        GroupRoom group = groupRoomRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        for (String gifticonNumber : dto.getGifticonNumbers()) {
            Gifticon gifticon = gifticonRepository.findByGifticonNumber(gifticonNumber)
                    .orElseThrow(() -> new RuntimeException("Gifticon not found: " + gifticonNumber));
            groupGifticonRepository.save(new GroupGifticon(null, group, gifticon));
        }
    }

    public List<GroupRoomResponseDto> getMyGroupRooms(String token) {
        String userId = jwtUtil.getUserIdFromToken(token);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<GroupUser> groupUsers = groupUserRepository.findByUser(user);

        return groupUsers.stream()
                .map(gu -> new GroupRoomResponseDto(
                        gu.getGroup().getId(),
                        gu.getGroup().getRoomName()
                ))
                .collect(Collectors.toList());
    }

    public List<GifticonResponseDto> getGifticonsByGroup(Long groupId, String token) {
        String userId = jwtUtil.getUserIdFromToken(token);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GroupRoom group = groupRoomRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        boolean isMember = groupUserRepository.existsByGroupAndUser(group, user);
        if (!isMember) {
            throw new RuntimeException("Access denied: Not a member of group");
        }

        return groupGifticonRepository.findByGroup(group).stream()
                .map(gg -> {
                    Gifticon g = gg.getGifticon();
                    return new GifticonResponseDto(
                            g.getGifticonNumber(),
                            g.getBrand(),
                            g.getDueDate().toString(),
                            g.getPosterNickname(),
                            g.getImageUrl()
                    );
                })
                .collect(Collectors.toList());
    }

    public GroupNameResponseDto getGroupNameById(Long groupId, String token) {
        String userId = jwtUtil.getUserIdFromToken(token);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GroupRoom group = groupRoomRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        boolean isMember = groupUserRepository.existsByGroupAndUser(group, user);
        if (!isMember) {
            throw new RuntimeException("Access denied: Not a member of the group");
        }

        return new GroupNameResponseDto(group.getRoomName());
    }

}
