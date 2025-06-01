package com.scrooge.alddeulticon;

import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
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
    private GifticonRepository gifticonRepository;

    @Autowired
    private MypageTrashRepository mypageTrashRepository;

    @Test
    void 휴지통_추가_성공() {
        // given
        Long userId = 1L;

        Gifticon gifticon = Gifticon.builder()
                .posterUserId("tester")
                .posterNickname("Tester")
                .dueDate(LocalDate.now().plusDays(10))
                .brand("TestBrand")
                .build();
        gifticon.setGifticonNumber("GIFT123456"); // @Id로 사용되는 값이라고 가정
        gifticon = gifticonRepository.save(gifticon);

        // when
        MypageTrashResponseDto response = mypageTrashService.addToTrash(
                userId,
                gifticon.getGifticonNumber(),
                "사용자"
        );

        // then
        assertNotNull(response);
        assertEquals("GIFT123456", response.getGifticonId());
        assertEquals("사용자", response.getWhoUse());
        assertNotNull(response.getDeletedDate());
        assertNull(response.getUsedDate());
    }

    @Test
    void 휴지통_목록조회_성공() {
        // given
        Long userId = 1L;

        Gifticon gifticon = Gifticon.builder()
                .posterUserId("tester2")
                .posterNickname("Tester2")
                .dueDate(LocalDate.now().plusDays(5))
                .brand("Brand2")
                .build();
        gifticon.setGifticonNumber("GIFT654321");
        gifticon = gifticonRepository.save(gifticon);

        MypageTrash trash = MypageTrash.builder()
                .userId(userId)
                .gifticon(gifticon)
                .deletedDate(LocalDateTime.now())
                .whoUse("Tester2")
                .build();
        mypageTrashRepository.save(trash);

        // when
        List<MypageTrashResponseDto> result = mypageTrashService.getAllTrashByUserId(userId);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());

        MypageTrashResponseDto dto = result.get(0);
        assertEquals("GIFT654321", dto.getGifticonId());
        assertEquals("Tester2", dto.getWhoUse());
        assertNotNull(dto.getDeletedDate());
    }
}
