package godsang.backend.service;

import godsang.backend.entity.IntakeHistory;
import godsang.backend.entity.MealType;
import godsang.backend.entity.dto.IntakeRequestDto;
import godsang.backend.exception.EntityNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class IntakeServiceTest {

    private final IntakeService intakeService;

    @Autowired
    public IntakeServiceTest(IntakeService intakeService) {
        this.intakeService = intakeService;
    }

    @Test
    @DisplayName("해당 날짜에 아침, 점심, 저녁에 섭취한 음식 등록 테스트")
    @Rollback(value = false)
    @Transactional
    void registerIntakeFood() {
        List<IntakeRequestDto.RequestItem> items = new ArrayList<>();
        IntakeRequestDto.RequestItem item1 = new IntakeRequestDto.RequestItem(1L, 100);
        IntakeRequestDto.RequestItem item2 = new IntakeRequestDto.RequestItem(2L, 100);
        IntakeRequestDto.RequestItem item3= new IntakeRequestDto.RequestItem(3L, 100);
        items.add(item1);
        items.add(item2);
        items.add(item3);

        IntakeRequestDto requestDto = new IntakeRequestDto(1L, LocalDate.now(), MealType.MORNING,
                items);
        intakeService.registerIntakeFood(requestDto);
    }

    @Test
    @DisplayName("잘못된 member_id 조회")
    void findNonMemberId() {
        List<IntakeRequestDto.RequestItem> items = new ArrayList<>();
        IntakeRequestDto.RequestItem item1 = new IntakeRequestDto.RequestItem(1L, 100);
        IntakeRequestDto.RequestItem item2 = new IntakeRequestDto.RequestItem(2L, 100);
        IntakeRequestDto.RequestItem item3= new IntakeRequestDto.RequestItem(3L, 100);
        items.add(item1);
        items.add(item2);
        items.add(item3);

        IntakeRequestDto requestDto = new IntakeRequestDto(0L, LocalDate.now(), MealType.MORNING,
                items);

        assertThrows(
                EntityNotFoundException.class, () -> intakeService.registerIntakeFood(requestDto));
    }

    @Test
    @DisplayName("잘못된 Food_id 조회")
    void findNoneFoodId() {
        List<IntakeRequestDto.RequestItem> items = new ArrayList<>();
        IntakeRequestDto.RequestItem item1 = new IntakeRequestDto.RequestItem(0L, 100);
        items.add(item1);

        IntakeRequestDto requestDto = new IntakeRequestDto(1L, LocalDate.now(), MealType.MORNING,
                items);

        assertThrows(EntityNotFoundException.class, () ->
                intakeService.registerIntakeFood(requestDto));
    }

    @Test
    void intakeFoodList(){
//        Optional<IntakeHistory> intakeHistory = intakeService.intakeHistoryList(1L);
//        log.info(intakeHistory.get());
    }

//    @BeforeEach
//    @Rollback(value = false)
//    public void initIntakeFood() {
//        List<IntakeRequestDto.RequestItem> items = new ArrayList<>();
//        IntakeRequestDto.RequestItem item1 = new IntakeRequestDto.RequestItem(1L, 100);
//        IntakeRequestDto.RequestItem item2 = new IntakeRequestDto.RequestItem(2L, 100);
//        IntakeRequestDto.RequestItem item3= new IntakeRequestDto.RequestItem(3L, 100);
//        items.add(item1);
//        items.add(item2);
//        items.add(item3);
//
//        IntakeRequestDto requestDto = new IntakeRequestDto(1L, LocalDate.now(), MealType.MORNING,
//                items);
//        intakeService.registerIntakeFood(requestDto);
//    }
}