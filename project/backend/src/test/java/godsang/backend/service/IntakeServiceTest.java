package godsang.backend.service;

import godsang.backend.entity.*;
import godsang.backend.entity.dto.FoodDto;
import godsang.backend.entity.dto.IntakeRequestDto;
import godsang.backend.entity.dto.IntakeResponseDto;
import godsang.backend.entity.dto.RequestIIntakeHistory;
import godsang.backend.exception.EntityNotFoundException;
import godsang.backend.repository.FoodRepository;
import godsang.backend.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class IntakeServiceTest {

    private final IntakeService intakeService;
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public IntakeServiceTest(IntakeService intakeService, MemberRepository memberRepository,
                             FoodRepository foodRepository) {
        this.intakeService = intakeService;
        this.memberRepository = memberRepository;
        this.foodRepository = foodRepository;
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
    @DisplayName("해당 날짜에 맞게 아침, 점심, 저녁에 섭취한 음식들을 반환하고 섭취한 amount 만큼 변하는지 확인")
    @Transactional
    void RequestIntakeHistory() {
        RequestIIntakeHistory intakeHistory = new RequestIIntakeHistory(1L,
                LocalDate.of(2023, 2, 26));
        IntakeResponseDto intakeResponseDto = intakeService.intakeHistoryList(intakeHistory);

        for (FoodDto food : intakeResponseDto.getIntakeMap().get(MealType.MORNING)) {
            log.info("아침에 섭취한 음식들 = {}", food);
        }
        for (FoodDto food : intakeResponseDto.getIntakeMap().get(MealType.LUNCH)) {
            log.info("점심에 섭취한 음식들 = {}", food);
        }
        for (FoodDto food : intakeResponseDto.getIntakeMap().get(MealType.DINNER)) {
            log.info("저녁에 섭취한 음식들 = {}", food);
        }

        Food findByFood = foodRepository.findById(34L).orElseThrow(() ->
                new EntityNotFoundException("음식을 찾지 못했습니다."));
        FoodDto food = new FoodDto(findByFood);
        FoodDto intakeFood = intakeResponseDto.getIntakeMap().get(MealType.MORNING).get(2);
        log.info("Food 테이블에 저장되어 있는 음식 정보 = {}",food);
        log.info("IntakeFood 테이블에 저장되어 있는 amount 만큼 변환한 Food = {}",intakeFood);
        org.assertj.core.api.Assertions.assertThat(food).isNotSameAs(intakeFood);
    }
}