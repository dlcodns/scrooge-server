package com.scrooge.alddeulticon.domain.giftcorn.service;

import com.scrooge.alddeulticon.domain.giftcorn.dto.GiftcornRequestDto;
import com.scrooge.alddeulticon.domain.giftcorn.dto.GiftcornResponseDto;
import com.scrooge.alddeulticon.domain.giftcorn.entity.Giftcorn;
import com.scrooge.alddeulticon.domain.giftcorn.repository.GiftcornRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftcornService {

    private final GiftcornRepository giftcornRepository;

    public GiftcornResponseDto createGiftcorn(GiftcornRequestDto dto) {
        Giftcorn giftcorn = Giftcorn.builder()
                .whoPost(dto.getWhoPost())
                .whichRoom(dto.getWhichRoom())
                .dueDate(dto.getDueDate())
                .brand(dto.getBrand())
                .productName(dto.getProductName())
                .build();
        Giftcorn saved = giftcornRepository.save(giftcorn);
        return toResponseDto(saved);
    }

    public List<GiftcornResponseDto> getAllGiftcorns() {
        return giftcornRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public GiftcornResponseDto getGiftcorn(Long id) {
        return giftcornRepository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new IllegalArgumentException("해당 기프티콘을 찾을 수 없습니다."));
    }

    public GiftcornResponseDto updateGiftcorn(Long id, GiftcornRequestDto dto) {
        Giftcorn giftcorn = giftcornRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기프티콘을 찾을 수 없습니다."));
        giftcorn.setWhoPost(dto.getWhoPost());
        giftcorn.setWhichRoom(dto.getWhichRoom());
        giftcorn.setDueDate(dto.getDueDate());
        giftcorn.setBrand(dto.getBrand());
        giftcorn.setProductName(dto.getProductName());
        Giftcorn updated = giftcornRepository.save(giftcorn);
        return toResponseDto(updated);
    }

    public void deleteGiftcorn(Long id) {
        giftcornRepository.deleteById(id);
    }

    private GiftcornResponseDto toResponseDto(Giftcorn giftcorn) {
        return new GiftcornResponseDto(
                giftcorn.getGiftcornNumber(),
                giftcorn.getWhoPost(),
                giftcorn.getWhichRoom(),
                giftcorn.getDueDate(),
                giftcorn.getBrand(),
                giftcorn.getProductName()
        );
    }
}
