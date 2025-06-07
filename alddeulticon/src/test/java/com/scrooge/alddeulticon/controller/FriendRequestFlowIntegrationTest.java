package com.scrooge.alddeulticon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrooge.alddeulticon.domain.user.dto.FriendRequestDecisionDto;
import com.scrooge.alddeulticon.domain.user.dto.FriendRequestDto;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.repository.FriendRequestRepository;
import com.scrooge.alddeulticon.domain.user.repository.FriendshipRepository;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import com.scrooge.alddeulticon.global.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class FriendRequestFlowIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private FriendRequestRepository friendRequestRepository;
    @Autowired private FriendshipRepository friendshipRepository;

    private User userA;
    private User userB;
    private String tokenA;
    private String tokenB;

    @BeforeEach
    void setup() {
        // 초기화
        friendshipRepository.deleteAll();
        friendRequestRepository.deleteAll();
        userRepository.deleteAll();

        userA = userRepository.save(User.builder()
                .userId("userA_" + System.nanoTime())
                .nickname("A")
                .password("1234")
                .region("서울")
                .email("a@email.com")
                .phone("010-0000-0000")
                .build());

        userB = userRepository.save(User.builder()
                .userId("userB_" + System.nanoTime())
                .nickname("B")
                .password("1234")
                .region("부산")
                .email("b@email.com")
                .phone("010-1111-1111")
                .build());

        tokenA = jwtUtil.generateToken(userA.getUserId());
        tokenB = jwtUtil.generateToken(userB.getUserId());
    }

    @Test
    void 친구요청_수락_후_친구목록조회까지_성공() throws Exception {
        // 1. A → B 친구 요청
        FriendRequestDto requestDto = new FriendRequestDto(userB.getId());
        mockMvc.perform(post("/api/friends/request")
                        .header("Authorization", "Bearer " + tokenA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // 2. B가 친구 요청 수락
        FriendRequestDecisionDto acceptDto = new FriendRequestDecisionDto(userA.getId());
        mockMvc.perform(post("/api/friends/accept")
                        .header("Authorization", "Bearer " + tokenB)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(acceptDto)))
                .andExpect(status().isOk());

        // 3. A가 친구 목록 조회 → B가 포함되어 있어야 함
        mockMvc.perform(get("/api/friends")
                        .header("Authorization", "Bearer " + tokenA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userB.getId()))
                .andExpect(jsonPath("$[0].nickname").value(userB.getNickname()));
    }
}
