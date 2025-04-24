package godsang.backend.service;

import godsang.backend.entity.Food;
import godsang.backend.entity.IntakeFood;
import godsang.backend.entity.IntakeHistory;
import godsang.backend.entity.Member;
import godsang.backend.entity.dto.IntakeRequestDto;
import godsang.backend.exception.EntityNotFoundException;
import godsang.backend.repository.FoodRepository;
import godsang.backend.repository.IntakeFoodRepository;
import godsang.backend.repository.IntakeHistoryRepository;
import godsang.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IntakeService {

    private final MemberRepository memberRepository;
    private final IntakeHistoryRepository intakeHistoryRepository;
    private final IntakeFoodRepository intakeFoodRepository;
    private final FoodRepository foodRepository;

//    @Transactional
//    public void registerIntakeFood(IntakeRequestDto requestDto) {
//        Optional<Member> findMember = memberRepository.findById(requestDto.getMemberId());
//        if (findMember.isEmpty())
//            throw new EntityNotFoundException("Not Found Member Entity");
//
//        log.info("member = {}", findMember.get());
//        IntakeHistory intakeHistory = IntakeHistory.builder().id(null).member(findMember.get())
//                .intakeDate(requestDto.getDate()).mealType(requestDto.getMealType())
//                .build();
//        intakeHistoryRepository.save(intakeHistory);
//
//        for (IntakeRequestDto.RequestItem request : requestDto.getItems()) {
//            Optional<Food> getFood = foodRepository.findById(request.getFooId());
//
//            Food food = getFood.orElseThrow(() ->
//                    new EntityNotFoundException("음식을 찾을 수 없습니다."));
//
//            IntakeFood intakeFood = IntakeFood.builder()/*.id(null).intakeHistory(intakeHistory)*/
//                    .food(food).amount(request.getGram()).build();
//
//            intakeFoodRepository.save(intakeFood);
//            log.info("저장된 IntakeFood = {}", intakeFood);
//        }
//
//        log.info("저장된 IntakeHistory = {}", intakeHistory);
//        log.info("저장된 IntakeHistory.IntakeFoods = {}", intakeHistory.getIntakeFoods());
//    }

    @Transactional
    public void registerIntakeFood(IntakeRequestDto requestDto) {
        // 1. Member 조회
        log.info("IntakeRequest DTO = ", requestDto);
        log.info("Member 조회 시작");
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 2. IntakeHistory 생성
        log.info("IntakeHistory 생성");
        IntakeHistory intakeHistory = IntakeHistory.builder()
                .member(member)
                .mealType(requestDto.getMealType())
                .intakeFoods(new ArrayList<>())
                .intakeDate(requestDto.getDate())
                .build();

        // 3. RequestItem을 통해 IntakeFood 생성
        log.info("IntakeFood 생성");
        log.info("Food ID = {}", requestDto.getItems().get(0).getFoodId());
        for (IntakeRequestDto.RequestItem item : requestDto.getItems()) {
            log.info("requestDto.Items 비어 있니?");
            log.info("Food = {}", item);
            Food food = foodRepository.findById(item.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            log.info("IntakeFood 객체 생성");
            IntakeFood intakeFood = IntakeFood.builder()
                    .food(food)
                    .amount(item.getGram())
                    .build();

            log.info("IntakeHistory.addIntakeFood");
            // 4. IntakeHistory에 IntakeFood 추가
            intakeHistory.addIntakeFood(intakeFood);
        }

        log.info("IntakeHistory 저장");
        // 5. IntakeHistory 저장 (Cascade로 IntakeFood도 함께 저장됨)
        intakeHistoryRepository.save(intakeHistory);
    }

    //Todo
    public IntakeHistory intakeHistoryList(Long id) {
        Optional<IntakeHistory> findHistory = intakeHistoryRepository.findById(id);
        IntakeHistory intakeHistory = findHistory.orElseThrow(
                () -> new EntityNotFoundException("히스토리 찾을 수 없음"));
        return intakeHistory;
    }
}
