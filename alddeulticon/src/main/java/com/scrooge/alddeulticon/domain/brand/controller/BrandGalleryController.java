package com.scrooge.alddeulticon.domain.brand.controller;

import com.scrooge.alddeulticon.domain.brand.dto.BrandGalleryResponseDto;
import com.scrooge.alddeulticon.domain.brand.service.BrandGalleryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brand-gallery")
@RequiredArgsConstructor
public class BrandGalleryController {

    private final BrandGalleryService brandGalleryService;

    @GetMapping
    public BrandGalleryResponseDto getMyBrandGallery(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return brandGalleryService.getBrandGallery(token);
    }
}
