package com.scrooge.alddeulticon.domain.preference.init;

import com.scrooge.alddeulticon.domain.preference.entity.GifticonKeyword;
import com.scrooge.alddeulticon.domain.preference.repository.GifticonKeywordRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GifticonKeywordInitializer {

    private final GifticonKeywordRepository keywordRepository;

    @PostConstruct
    public void initKeywords() {
        List<String> keywords = List.of("치킨", "커피", "떡볶이", "쿠키", "편의점", "케이크", "배민 상품권", "인테리어");
        for (String word : keywords) {
            if (!keywordRepository.existsByName(word)) {
                keywordRepository.save(new GifticonKeyword(word));
            }
        }
    }
}

