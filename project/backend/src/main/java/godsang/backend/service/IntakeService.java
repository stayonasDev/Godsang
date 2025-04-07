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

    @Transactional
    public void registerIntakeFood(IntakeRequestDto requestDto) {
        Optional<Member> findMember = memberRepository.findById(requestDto.getMemberId());
        if(findMember.isEmpty())
            throw new EntityNotFoundException("Not Found Member Entity");

        IntakeHistory intakeHistory = IntakeHistory.builder().id(null).member(findMember.get())
                .intakeDate(requestDto.getDate()).mealType(requestDto.getMealType())
                .build();
        intakeHistoryRepository.save(intakeHistory);

        for (IntakeRequestDto.RequestItem request : requestDto.getItems()) {
            Optional<Food> getFood = foodRepository.findById(request.getFooId());

            Food food = getFood.orElseThrow(() ->
                    new EntityNotFoundException("음식을 찾을 수 없습니다."));

            IntakeFood intakeFood = IntakeFood.builder().id(null).intakeHistory(intakeHistory)
                    .food(food).amount(request.getGram()).build();
            intakeFoodRepository.save(intakeFood);
//            log.info("저장된 IntakeFood = {}", intakeFood);
        }
//        log.info("저장된 IntakeHistory = {}", intakeHistory);
    }
}
