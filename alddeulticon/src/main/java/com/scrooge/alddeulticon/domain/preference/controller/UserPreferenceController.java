package com.scrooge.alddeulticon.domain.preference.controller;

import com.scrooge.alddeulticon.domain.preference.dto.GifticonKeywordDto;
import com.scrooge.alddeulticon.domain.preference.dto.PreferenceResponse;
import com.scrooge.alddeulticon.domain.preference.dto.PreferenceSaveRequest;
import com.scrooge.alddeulticon.domain.preference.service.UserPreferenceService;
import com.scrooge.alddeulticon.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService preferenceService;

    @PostMapping("/preferences")
    public ResponseEntity<String> savePreference(@RequestBody PreferenceSaveRequest request,
                                                 @AuthenticationPrincipal CustomUserDetails user) {
        preferenceService.savePreference(user.getId(), request);
        return ResponseEntity.ok("선호 키워드가 저장되었습니다.");
    }

    @GetMapping("/preferences/me")
    public ResponseEntity<PreferenceResponse> getMyPreference(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(preferenceService.getMyPreference(user.getId()));
    }

    @GetMapping("/preferences/{userId}")
    public ResponseEntity<PreferenceResponse> getOtherPreference(@PathVariable Long userId) {
        return ResponseEntity.ok(preferenceService.getOtherPreference(userId));
    }

    @GetMapping("/keywords")
    public ResponseEntity<List<GifticonKeywordDto>> getKeywords() {
        return ResponseEntity.ok(preferenceService.getAllKeywords());
    }
}