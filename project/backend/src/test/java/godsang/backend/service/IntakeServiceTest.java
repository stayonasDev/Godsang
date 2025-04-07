package godsang.backend.service;

import godsang.backend.entity.MealType;
import godsang.backend.entity.dto.IntakeRequestDto;
import godsang.backend.exception.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IntakeServiceTest {

    private final IntakeService intakeService;

    @Autowired
    public IntakeServiceTest(IntakeService intakeService) {
        this.intakeService = intakeService;
    }

    @Test
    @DisplayName("해당 날짜에 아침, 점심, 저녁에 섭취한 음식 등록 테스트")
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
}