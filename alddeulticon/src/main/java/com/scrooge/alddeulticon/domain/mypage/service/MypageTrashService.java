package com.scrooge.alddeulticon.domain.mypage.service;

import com.scrooge.alddeulticon.domain.mypage.entity.MypageTrash;
import com.scrooge.alddeulticon.domain.mypage.dto.MypageTrashResponseDto;
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

    @Transactional(readOnly = true)
    public List<MypageTrashResponseDto> getAllTrashByUserId(Long userId) {
        return mypageTrashRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MypageTrashResponseDto addToTrash(Long userId, Long giftcornId, String whoUse) {
        MypageTrash trash = MypageTrash.builder()
                .userId(userId)
                .giftcornId(giftcornId)
                .deletedDate(LocalDateTime.now())
                .whoUse(whoUse)
                .usedDate(null)
                .build();

        mypageTrashRepository.save(trash);
        return toDto(trash);
    }

    private MypageTrashResponseDto toDto(MypageTrash trash) {
        MypageTrashResponseDto dto = new MypageTrashResponseDto();
        dto.setId(trash.getId());
        dto.setUserId(trash.getUserId());
        dto.setGiftcornId(trash.getGiftcornId());
        dto.setWhoUse(trash.getWhoUse());
        dto.setDeletedDate(trash.getDeletedDate());
        dto.setUsedDate(trash.getUsedDate());
        return dto;
    }
}
