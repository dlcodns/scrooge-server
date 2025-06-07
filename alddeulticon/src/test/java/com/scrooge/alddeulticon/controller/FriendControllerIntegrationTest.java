package com.scrooge.alddeulticon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrooge.alddeulticon.domain.user.dto.FriendRequestDto;
import com.scrooge.alddeulticon.domain.user.dto.FriendRequestDecisionDto;
import com.scrooge.alddeulticon.domain.user.entity.Friendship;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.repository.FriendRequestRepository;
import com.scrooge.alddeulticon.domain.user.repository.FriendshipRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class FriendControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    private User me;
    private User other;

    @BeforeEach
    void setUp() {
        friendshipRepository.deleteAll();
        friendRequestRepository.deleteAll();
        userRepository.deleteAll();
        SecurityContextHolder.clearContext();

        me = userRepository.save(User.builder()
                .userId("me_" + System.nanoTime())
                .nickname("나")
                .password("pw")
                .region("서울")
                .email("me@example.com")
                .phone("010-0000-0000")
                .build());

        other = userRepository.save(User.builder()
                .userId("you_" + System.nanoTime())
                .nickname("너")
                .password("pw")
                .region("부산")
                .email("you@example.com")
                .phone("010-1111-1111")
                .build());

        // 로그인 사용자 시큐리티 등록
        CustomUserDetails userDetails = new CustomUserDetails(me);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 양방향 Friendship 미리 삽입
        friendshipRepository.save(Friendship.builder().user(me).friend(other).build());
        friendshipRepository.save(Friendship.builder().user(other).friend(me).build());

    }

    @Test
    void 친구목록_정상조회() throws Exception {
        String token = jwtUtil.generateToken(me.getUserId());

        mockMvc.perform(get("/api/friends")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(other.getId()))
                .andExpect(jsonPath("$[0].nickname").value(other.getNickname()));
    }
}
