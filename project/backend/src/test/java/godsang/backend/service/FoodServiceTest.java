package godsang.backend.service;

import godsang.backend.entity.Food;
import godsang.backend.entity.dto.FoodDto;
import godsang.backend.repository.FoodRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Transactional
@Rollback(value = false)
class FoodServiceTest {

    private final FoodService foodService;
    private final FoodRepository repository;
    private final EntityManager em;

    @Autowired
    public FoodServiceTest(FoodService foodService, FoodRepository repository, EntityManager em) {
        this.foodService = foodService;
        this.repository = repository;
        this.em = em;
    }

    //첫 번쨰 에러 이유 Equal 메서드가 정의되지 않아 실패
    @Test
    void foodEdit() {
        FoodDto foodDto = FoodDto.builder().id(200L).kal(100).name("치킨").carb(1).protein(10).pat(1)
                .sugar(0).cholest(0).sodium(0).build();
        FoodDto editFood = foodService.foodEdit(foodDto);
        log.info("editFood = {} ", editFood);

        Optional<Food> optionalFood = repository.findById(editFood.getId());
        Assertions.assertThat(editFood.foodConversion()).isEqualTo(optionalFood.get());
    }
}