package com.scrooge.alddeulticon.domain.user.service;

import com.scrooge.alddeulticon.domain.user.dto.*;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.global.exception.type.ErrorCode;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import com.scrooge.alddeulticon.global.exception.CustomException;
import com.scrooge.alddeulticon.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserSignupResponseDto signup(UserSignupRequestDto dto) {
        // 중복 사용자 아이디 체크
        if (userRepository.findByUserId(dto.getUserId()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_ID);
        }

        // User 엔티티 생성 및 저장
        User user = User.builder()
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .region(dto.getRegion())
                .nickname(dto.getNickname())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();

        userRepository.save(user);
        return new UserSignupResponseDto(user.getId(), user.getNickname(), "회원가입이 완료되었습니다.");
    }

    public UserLoginResponseDto login(UserLoginRequestDto dto) {
        // 사용자 조회, 없으면 예외
        User user = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 불일치시 예외
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성 및 DTO 반환
        String token = jwtUtil.generateToken(user.getUserId());

        return new UserLoginResponseDto(user.getId(), user.getNickname(), token);
    }

    public UserSearchResponseDto searchByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserSearchResponseDto(user.getId(), user.getNickname());
    }

}
