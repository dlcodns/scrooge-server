package com.scrooge.alddeulticon.domain.user.service;

import com.scrooge.alddeulticon.domain.user.dto.FriendRequestDecisionDto;
import com.scrooge.alddeulticon.domain.user.dto.FriendRequestDto;
import com.scrooge.alddeulticon.domain.user.dto.UserFriendDto;
import com.scrooge.alddeulticon.domain.user.entity.FriendRequest;
import com.scrooge.alddeulticon.domain.user.entity.Friendship;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.user.enums.RequestStatus;
import com.scrooge.alddeulticon.domain.user.repository.FriendRequestRepository;
import com.scrooge.alddeulticon.domain.user.repository.FriendshipRepository;
import com.scrooge.alddeulticon.domain.user.repository.UserRepository;
import com.scrooge.alddeulticon.global.exception.CustomException;
import com.scrooge.alddeulticon.global.exception.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;

    public void sendFriendRequest(Long senderId, FriendRequestDto dto) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (sender.equals(receiver)) {
            throw new CustomException(ErrorCode.SELF_REQUEST);
        }

        boolean alreadyRequested = friendRequestRepository.existsBySenderAndReceiverAndStatus(
                sender, receiver, RequestStatus.PENDING
        );

        if (alreadyRequested) {
            throw new CustomException(ErrorCode.DUPLICATE_REQUEST);
        }

        FriendRequest request = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(RequestStatus.PENDING)
                .build();

        friendRequestRepository.save(request);
    }

    public void acceptFriendRequest(Long userId, FriendRequestDecisionDto dto) {
        FriendRequest request = friendRequestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        // 요청의 수신자인지 확인
        if (!request.getReceiver().getId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        request.setStatus(RequestStatus.ACCEPTED);
        friendRequestRepository.save(request);

        // 친구 관계 양방향으로 저장
        friendshipRepository.save(Friendship.builder()
                .user(request.getSender())
                .friend(request.getReceiver())
                .build());

        friendshipRepository.save(Friendship.builder()
                .user(request.getReceiver())
                .friend(request.getSender())
                .build());
    }

    public void rejectFriendRequest(Long userId, FriendRequestDecisionDto dto) {
        FriendRequest request = friendRequestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        if (!request.getReceiver().getId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        request.setStatus(RequestStatus.REJECTED);
        friendRequestRepository.save(request);
    }

    public List<UserFriendDto> getMyFriends(Long userId) {
        List<Friendship> friendships = friendshipRepository.findAllByUserId(userId);

        return friendships.stream()
                .map(f -> new UserFriendDto(f.getFriend().getId(), f.getFriend().getNickname()))
                .toList();
    }

}
