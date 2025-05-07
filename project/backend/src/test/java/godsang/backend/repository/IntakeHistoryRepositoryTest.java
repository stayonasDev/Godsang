package godsang.backend.repository;

import godsang.backend.entity.IntakeHistory;
import godsang.backend.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class IntakeHistoryRepositoryTest {

    private final IntakeHistoryRepository repository;
    private final MemberRepository memberRepository;

    @Autowired
    public IntakeHistoryRepositoryTest(IntakeHistoryRepository repository, MemberRepository memberRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
    }

    @Test
    @Transactional
    //트랜젝션이 없다면 IntakeFoods 에서 Food 를 가져오지 못한다.
    void findHistoryForDate(){
//        Optional<Member> member = memberRepository.findById(1L);
//        IntakeHistory historyForDate = repository.findHistoryForDate(LocalDate.of(2023, 2, 26), member.get());
//        log.info("History = {}", historyForDate);
//        log.info(historyForDate.getIntakeFoods());
//
//        log.info("IntakeFoods.Food = {}", historyForDate.getIntakeFoods().get(0).getFood().getName());
    }
}