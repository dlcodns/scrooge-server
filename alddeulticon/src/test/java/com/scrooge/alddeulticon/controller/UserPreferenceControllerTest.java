package com.scrooge.alddeulticon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrooge.alddeulticon.domain.preference.dto.PreferenceSaveRequest;
import com.scrooge.alddeulticon.domain.preference.entity.GifticonKeyword;
import com.scrooge.alddeulticon.domain.preference.repository.GifticonKeywordRepository;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import com.scrooge.alddeulticon.global.security.CustomUserDetails;
import com.scrooge.alddeulticon.global.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class UserPreferenceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GifticonKeywordRepository keywordRepository;

    private Long firstKeywordId;
    private Long secondKeywordId;
    private Long thirdKeywordId;
    private String testUserId; // JWT 생성용으로 저장

    @BeforeEach
    void setupUserAndKeywords() {
        SecurityContextHolder.clearContext(); // 초기화

        keywordRepository.deleteAll();
        userRepository.deleteAll();

        GifticonKeyword coffee = keywordRepository.save(GifticonKeyword.builder().name("커피_" + System.nanoTime()).build());
        GifticonKeyword chicken = keywordRepository.save(GifticonKeyword.builder().name("치킨_" + System.nanoTime()).build());
        GifticonKeyword burger = keywordRepository.save(GifticonKeyword.builder().name("햄버거_" + System.nanoTime()).build());

        firstKeywordId = coffee.getId();
        secondKeywordId = chicken.getId();
        thirdKeywordId = burger.getId();

        testUserId = "testuser_" + System.nanoTime();
        User user = userRepository.save(User.builder()
                .userId(testUserId)
                .nickname("테스터")
                .password("1234")
                .region("서울")
                .email("test@email.com")
                .phone("010-0000-0000")
                .build());

        // 시큐리티 컨텍스트 설정
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void 선호도저장_API_실제_DB로_테스트() throws Exception {
        PreferenceSaveRequest request = new PreferenceSaveRequest(firstKeywordId, secondKeywordId, thirdKeywordId);
        String json = objectMapper.writeValueAsString(request);

        // 🔐 JWT 발급
        String token = jwtUtil.generateToken(testUserId);

        mockMvc.perform(post("/api/preferences")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
