package com.scrooge.alddeulticon;

import com.scrooge.alddeulticon.domain.gifticon.entity.Gifticon;
import com.scrooge.alddeulticon.domain.gifticon.repository.GifticonRepository;
import com.scrooge.alddeulticon.domain.group.entity.Group;
import com.scrooge.alddeulticon.domain.group.repository.GroupRepository;
import com.scrooge.alddeulticon.domain.groupgifticon.entity.GroupGifticon;
import com.scrooge.alddeulticon.domain.groupgifticon.repository.GroupGifticonRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class GifticonServiceTest {

    @Autowired
    private GifticonRepository gifticonRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupGifticonRepository groupGifticonRepository;

    @Test
    void 기프티콘_등록_성공() {
        Gifticon gifticon = Gifticon.builder()
                .gifticonNumber("1234 5678 9000")
                .whoPost("chaeun")
                .whichRoom("시간별")
                .dueDate(LocalDate.of(2026, 5, 30))
                .brand("스타벅스")
                .productName("아이스 아메리카노")
                .build();

        Gifticon saved = gifticonRepository.save(gifticon);

        assertNotNull(saved);
        assertEquals("스타벅스", saved.getBrand());
        assertEquals("1234 5678 9000", saved.getGifticonNumber());
    }

    @Test
    void 내_기프티콘_조회() {
        gifticonRepository.save(Gifticon.builder()
                .gifticonNumber("1111 2222 3333")
                .whoPost("chaeun")
                .whichRoom("시간별")
                .dueDate(LocalDate.of(2026, 6, 1))
                .brand("투썸")
                .productName("케이크")
                .build());

        List<Gifticon> result = gifticonRepository.findAll()
                .stream()
                .filter(g -> g.getWhoPost().equals("chaeun"))
                .toList();

        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(g -> g.getWhoPost().equals("chaeun")));
    }

    @Test
    void 그룹방에_기프티콘_공유() {
        Gifticon gifticon = gifticonRepository.save(Gifticon.builder()
                .gifticonNumber("4444 5555 6666")
                .whoPost("chaeun")
                .whichRoom("시간별")
                .dueDate(LocalDate.now())
                .brand("BBQ")
                .productName("황올반")
                .build());

        Group group = groupRepository.save(Group.builder().name("친구방").build());

        GroupGifticon shared = groupGifticonRepository.save(
                GroupGifticon.builder()
                        .groupId(group.getId())
                        .gifticonNumber(gifticon.getGifticonNumber())
                        .build());

        assertNotNull(shared);
        assertEquals(group.getId(), shared.getGroupId());
    }

    @Test
    void 그룹별_조회_유효기간_순() {
        Group group = groupRepository.save(Group.builder().name("가족방").build());

        gifticonRepository.save(Gifticon.builder()
                .gifticonNumber("0000 0000 0001")
                .whoPost("chaeun")
                .whichRoom("가족방")
                .dueDate(LocalDate.of(2026, 4, 10))
                .brand("스타벅스")
                .productName("콜드브루")
                .build());

        gifticonRepository.save(Gifticon.builder()
                .gifticonNumber("0000 0000 0002")
                .whoPost("chaeun")
                .whichRoom("가족방")
                .dueDate(LocalDate.of(2026, 3, 10))
                .brand("스타벅스")
                .productName("프라푸치노")
                .build());

        List<Gifticon> sorted = gifticonRepository.findAll().stream()
                .filter(g -> g.getWhichRoom().equals("가족방"))
                .sorted((g1, g2) -> g1.getDueDate().compareTo(g2.getDueDate()))
                .toList();

        assertEquals("0000 0000 0002", sorted.get(0).getGifticonNumber());
    }

    @Test
    void 브랜드별_조회() {
        gifticonRepository.save(Gifticon.builder()
                .gifticonNumber("0000 1111 2222")
                .whoPost("chaeun")
                .whichRoom("시간별")
                .dueDate(LocalDate.of(2026, 7, 1))
                .brand("이디야")
                .productName("토피넛")
                .build());

        List<Gifticon> result = gifticonRepository.findAll().stream()
                .filter(g -> g.getBrand().equals("이디야"))
                .toList();

        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(g -> g.getBrand().equals("이디야")));
    }

    @Test
    void 줄글_뷰_데이터_포맷_테스트() {
        Gifticon gifticon = gifticonRepository.save(Gifticon.builder()
                .gifticonNumber("9999 8888 7777")
                .whoPost("chaeun")
                .whichRoom("시간별")
                .dueDate(LocalDate.of(2026, 8, 1))
                .brand("던킨")
                .productName("도넛세트")
                .build());

        assertNotNull(gifticon.getBrand());
        assertNotNull(gifticon.getProductName());
        assertNotNull(gifticon.getDueDate());
    }
}
