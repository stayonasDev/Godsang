package godsang.backend.service;

import godsang.backend.entity.Food;
import godsang.backend.entity.dto.FoodDto;
import godsang.backend.entity.dto.PageRequestDto;
import godsang.backend.exception.EntityNotFoundException;
import godsang.backend.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class FoodService {

    private final FoodRepository foodRepository;

    //TODO
    @Transactional
    public FoodDto foodRegister(FoodDto foodDto){
        Food food = foodDto.foodConversion();
        foodRepository.save(food);
        return new FoodDto(food);
    }

    public Page<FoodDto> foodList(String foodName, PageRequestDto pageDto) {
        Optional<Food> foodFind = foodRepository.findFirstByNameContaining(foodName);

        if(foodFind.isEmpty())
            throw new EntityNotFoundException("해당 " + foodName + " 이라는 음식을 찾을 수 없습니다.");

        Sort sort = Sort.by("fo_id").descending();
        Pageable pageable = pageDto.getPageable(sort);
        return foodRepository.searchOneFoodName(pageable, foodName);
    }

}
