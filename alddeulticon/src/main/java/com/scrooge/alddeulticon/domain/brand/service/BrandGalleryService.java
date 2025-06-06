package com.scrooge.alddeulticon.domain.brand.service;

import com.scrooge.alddeulticon.domain.brand.dto.*;
import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import com.scrooge.alddeulticon.domain.group.repository.GroupGifticonRepository;
import com.scrooge.alddeulticon.domain.group.repository.GroupUserRepository;
import com.scrooge.alddeulticon.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandGalleryService {

    private final GifticonRepository gifticonRepository;
    private final GroupUserRepository groupUserRepository;
    private final GroupGifticonRepository groupGifticonRepository;
    private final JwtUtil jwtUtil;

    public BrandGalleryResponseDto getBrandGallery(String token) {
        String userId = jwtUtil.getUserIdFromToken(token);

        // 1. 내가 속한 그룹 ID들 조회
        List<Long> groupIds = groupUserRepository.findGroupIdsByUserId(userId);

        // 2. 그룹에 등록된 기프티콘 번호 조회
        Set<String> groupGifticonNumbers = groupGifticonRepository.findByGroupIds(groupIds)
                .stream()
                .map(gg -> gg.getGifticon().getGifticonNumber())
                .collect(Collectors.toSet());

        // 3. 내가 등록한 기프티콘 번호 조회
        Set<String> myGifticonNumbers = gifticonRepository.findByPosterUserId(userId)
                .stream()
                .map(Gifticon::getGifticonNumber)
                .collect(Collectors.toSet());

        // 4. 전체 기프티콘 번호 합치기
        Set<String> allNumbers = new HashSet<>(groupGifticonNumbers);
        allNumbers.addAll(myGifticonNumbers);

        // 5. 번호 기반으로 기프티콘 조회
        List<Gifticon> gifticons = gifticonRepository.findAllByGifticonNumberIn(allNumbers);

        // 6. 브랜드별로 DTO 변환 및 그룹화
        Map<String, List<BrandGifticonDto>> grouped = gifticons.stream()
                .collect(Collectors.groupingBy(
                        Gifticon::getBrand,
                        Collectors.mapping(g -> new BrandGifticonDto(
                                g.getGifticonNumber(),
                                g.getBrand(),
                                g.getDueDate().toString(),
                                g.getPosterNickname(),
                                g.getPosterUserId()
                        ), Collectors.toList())
                ));

        // 7. 브랜드 단위로 정리
        List<BrandGroupDto> brands = grouped.entrySet().stream()
                .map(entry -> new BrandGroupDto(
                        entry.getKey(),
                        entry.getValue(),
                        entry.getValue().stream()
                                .map(BrandGifticonDto::getGifticonNumber)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new BrandGalleryResponseDto(brands.size(), brands);
    }
}
