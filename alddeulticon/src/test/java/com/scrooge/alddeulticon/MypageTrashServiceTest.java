package com.scrooge.alddeulticon;

import com.scrooge.alddeulticon.domain.giftcorn.entity.Giftcorn;
import com.scrooge.alddeulticon.domain.giftcorn.repository.GiftcornRepository;
import com.scrooge.alddeulticon.domain.mypage.dto.MypageTrashResponseDto;
import com.scrooge.alddeulticon.domain.mypage.entity.MypageTrash;
import com.scrooge.alddeulticon.domain.mypage.repository.MypageTrashRepository;
import com.scrooge.alddeulticon.domain.mypage.service.MypageTrashService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class MypageTrashServiceTest {

    @Autowired
    private MypageTrashService mypageTrashService;

    @Autowired
    private GiftcornRepository giftcornRepository;

    @Autowired
    private MypageTrashRepository mypageTrashRepository;

    @Test
    void 휴지통_추가_성공() {
        // given
        Long userId = 1L;

        Giftcorn giftcorn = Giftcorn.builder()
                .whoPost("테스터")
                .whichRoom("테스트방")
                .dueDate(LocalDate.now().plusDays(10))
                .brand("TestBrand")
                .productName("TestProduct")
                .build();
        giftcorn = giftcornRepository.save(giftcorn);

        // when
        MypageTrashResponseDto response = mypageTrashService.addToTrash(
                userId,
                giftcorn.getGiftcornNumber(),  // giftcornId와 일치
                "사용자"
        );

        // then
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(giftcorn.getGiftcornNumber(), response.getGiftcornId());
        assertNotNull(response.getDeletedDate());
        assertNull(response.getUsedDate());  // 명시적으로 null 확인
    }

    @Test
    void 휴지통_목록조회_성공() {
        // given
        Long userId = 1L;

        Giftcorn giftcorn = Giftcorn.builder()
                .whoPost("테스터2")
                .whichRoom("테스트방2")
                .dueDate(LocalDate.now().plusDays(5))
                .brand("Brand2")
                .productName("Product2")
                .build();
        giftcorn = giftcornRepository.save(giftcorn);

        MypageTrash trash = MypageTrash.builder()
                .userId(userId)
                .giftcornId(giftcorn.getGiftcornNumber())
                .deletedDate(LocalDateTime.now())
                .whoUse("테스터2")
                .build();
        mypageTrashRepository.save(trash);

        // when
        List<MypageTrashResponseDto> result = mypageTrashService.getAllTrashByUserId(userId);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        MypageTrashResponseDto dto = result.get(0);
        assertEquals(userId, dto.getUserId());
        assertEquals(giftcorn.getGiftcornNumber(), dto.getGiftcornId());
        assertNotNull(dto.getDeletedDate());
    }
}