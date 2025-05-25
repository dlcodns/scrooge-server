package com.scrooge.alddeulticon.service;

import com.scrooge.alddeulticon.domain.user.dto.UserLoginRequestDto;
import com.scrooge.alddeulticon.domain.user.dto.UserLoginResponseDto;
import com.scrooge.alddeulticon.domain.user.dto.UserSignupRequestDto;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import com.scrooge.alddeulticon.domain.user.service.UserService;
import com.scrooge.alddeulticon.global.exception.CustomException;
import com.scrooge.alddeulticon.global.exception.type.ErrorCode;
import com.scrooge.alddeulticon.global.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    void 회원가입_성공() {
        // given: 신규 유저 DTO
        UserSignupRequestDto dto = new UserSignupRequestDto();
        dto.setUserId("newuser");
        dto.setPassword("password");
        dto.setNickname("닉네임");

        when(userRepository.findByUserId("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // when
        userService.signup(dto);

        // then: 저장이 호출됨
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 회원가입_아이디중복_예외발생() {
        // given: 이미 존재하는 아이디
        UserSignupRequestDto dto = new UserSignupRequestDto();
        dto.setUserId("existinguser");

        when(userRepository.findByUserId("existinguser")).thenReturn(Optional.of(new User()));

        // when & then: 예외 발생
        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.signup(dto);
        });

        assertEquals(ErrorCode.DUPLICATE_USER_ID, exception.getErrorCode());
    }

    @Test
    void 로그인_성공() {
        // given: 정상 유저 및 비밀번호
        UserLoginRequestDto dto = new UserLoginRequestDto();
        dto.setUserId("testuser");
        dto.setPassword("password");

        User user = User.builder()
                .id(1L)
                .userId("testuser")
                .password("encodedPassword")
                .nickname("닉네임")
                .build();

        when(userRepository.findByUserId("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("testuser")).thenReturn("token123");

        // when
        UserLoginResponseDto response = userService.login(dto);

        // then: 반환값 확인
        assertEquals(user.getId(), response.getUserId());
        assertEquals(user.getNickname(), response.getNickname());
        assertEquals("token123", response.getToken());
    }

    @Test
    void 로그인_존재하지않는아이디_예외발생() {
        // given: 존재하지 않는 유저 ID
        UserLoginRequestDto dto = new UserLoginRequestDto();
        dto.setUserId("notexist");

        when(userRepository.findByUserId("notexist")).thenReturn(Optional.empty());

        // when & then: 예외 발생
        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.login(dto);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 로그인_비밀번호불일치_예외발생() {
        // given: 비밀번호 틀린 상황
        UserLoginRequestDto dto = new UserLoginRequestDto();
        dto.setUserId("testuser");
        dto.setPassword("wrongpassword");

        User user = User.builder()
                .userId("testuser")
                .password("encodedPassword")
                .nickname("닉네임")
                .build();

        when(userRepository.findByUserId("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        // when & then: 예외 발생
        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.login(dto);
        });

        assertEquals(ErrorCode.INVALID_PASSWORD, exception.getErrorCode());
    }
}