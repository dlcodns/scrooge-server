package com.scrooge.alddeulticon.domain.preference.service;

import com.scrooge.alddeulticon.domain.preference.dto.GifticonKeywordDto;
import com.scrooge.alddeulticon.domain.preference.dto.PreferenceResponse;
import com.scrooge.alddeulticon.domain.preference.dto.PreferenceSaveRequest;
import com.scrooge.alddeulticon.domain.preference.entity.GifticonKeyword;
import com.scrooge.alddeulticon.domain.preference.entity.UserPreference;
import com.scrooge.alddeulticon.domain.preference.repository.GifticonKeywordRepository;
import com.scrooge.alddeulticon.domain.preference.repository.UserPreferenceRepository;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {
    private final UserPreferenceRepository preferenceRepo;
    private final GifticonKeywordRepository keywordRepo;
    private final UserRepository userRepo;

    public void savePreference(Long userId, PreferenceSaveRequest request) {
        GifticonKeyword first = keywordRepo.findById(request.getFirstKeywordId()).orElseThrow();
        GifticonKeyword second = keywordRepo.findById(request.getSecondKeywordId()).orElseThrow();
        GifticonKeyword third = keywordRepo.findById(request.getThirdKeywordId()).orElseThrow();

        User user = userRepo.findById(userId).orElseThrow();
        UserPreference pref = preferenceRepo.findByUserId(userId).orElse(new UserPreference());

        pref.setFirst(first);
        pref.setSecond(second);
        pref.setThird(third);
        pref.setUser(user);

        preferenceRepo.save(pref);
    }

    public PreferenceResponse getMyPreference(Long userId) {
        UserPreference pref = preferenceRepo.findByUserId(userId).orElseThrow();
        return PreferenceResponse.builder()
                .first(pref.getFirst().getName())
                .second(pref.getSecond().getName())
                .third(pref.getThird().getName())
                .nickname(pref.getUser().getNickname())
                .build();
    }

    public PreferenceResponse getOtherPreference(Long userId) {
        UserPreference pref = preferenceRepo.findByUserId(userId).orElseThrow();
        return PreferenceResponse.builder()
                .first(pref.getFirst().getName())
                .second(pref.getSecond().getName())
                .third(pref.getThird().getName())
                .nickname(pref.getUser().getNickname())
                .build();
    }

    public List<GifticonKeywordDto> getAllKeywords() {
        return keywordRepo.findAll().stream()
                .map(k -> GifticonKeywordDto.builder().id(k.getId()).name(k.getName()).build())
                .toList();
    }
}
