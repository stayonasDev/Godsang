package godsang.backend.repository.search;

import godsang.backend.entity.Food;
import godsang.backend.entity.dto.FoodDto;
import godsang.backend.repository.FoodRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class FoodRepositoryCustomImplTest {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodRepositoryCustomImplTest(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Test
    @DisplayName(value = "Results Deprecated 맞춰 테스트")
    public void lookResult() {
        Page<FoodDto> foodDtos = foodRepository.searchFoodDto(PageRequest.of(0, 10));
        log.info("foodTotalElements = {}", foodDtos.getTotalElements());
        log.info("foodTotalPages = {}",foodDtos.getTotalPages());
        for (FoodDto foodDto : foodDtos) {
            log.info(foodDto);
        }
    }

    @Test
    @DisplayName(value = "음식 이름으로만 검색")
    public void findFoodName() {
        String foodName = "치킨";

        Sort sort = Sort.by("fo_id").ascending();
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Page<FoodDto> foodDtos = foodRepository.searchOneFoodName(pageRequest, foodName);

        log.info("현재 페에지 수 = {}",foodDtos.getTotalPages());
        log.info("총 데이터 수 = {}",foodDtos.getTotalElements());

        for (FoodDto foodDto : foodDtos) {
            log.info("foodDto = {}",foodDto);
        }
    }

    @PostConstruct
    @Rollback(value = false)
    public void init() {
        for (int i = 0; i < 100; i++) {
            foodRepository.save( new Food(null, "음식" + i, 100, 20, 20, 20,
                    0, 0, 0, 0, 1, 1, 1));
        }

        for (int i = 0; i < 100; i++) {
            foodRepository.save( new Food(null, "치킨" + i, 100, 20, 20, 20,
                    0, 0, 0, 0, 1, 1, 1));
        }
    }
}