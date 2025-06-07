package com.scrooge.alddeulticon.domain.mypage.service;

import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import com.scrooge.alddeulticon.domain.mypage.dto.MypageTrashResponseDto;
import com.scrooge.alddeulticon.domain.mypage.entity.MypageTrash;
import com.scrooge.alddeulticon.domain.mypage.repository.MypageTrashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageTrashService {

    private final MypageTrashRepository mypageTrashRepository;
    private final GifticonRepository gifticonRepository;

    @Transactional(readOnly = true)
    public List<MypageTrashResponseDto> getAllTrashByUserId(Long userId) {
        return mypageTrashRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MypageTrashResponseDto addToTrash(Long userId, String gifticonId, String whoUse) {
        Gifticon gifticon = gifticonRepository.findByGifticonNumber(gifticonId)
                .orElseThrow(() -> new IllegalArgumentException("해당 gifticonId를 찾을 수 없습니다."));

        MypageTrash trash = MypageTrash.builder()
                .userId(userId)
                .gifticon(gifticon)
                .deletedDate(LocalDateTime.now())
                .whoUse(whoUse)
                .usedDate(LocalDateTime.now())
                .build();

        mypageTrashRepository.save(trash);
        return toDto(trash);
    }

    private MypageTrashResponseDto toDto(MypageTrash trash) {
        return MypageTrashResponseDto.builder()
                .id(trash.getId())
                .gifticonId(trash.getGifticon().getGifticonNumber())
                .gifticonName(trash.getGifticon().getBrand())
                .whoUse(trash.getWhoUse())
                .deletedDate(trash.getDeletedDate())
                .usedDate(trash.getUsedDate())
                .build();
    }
}
