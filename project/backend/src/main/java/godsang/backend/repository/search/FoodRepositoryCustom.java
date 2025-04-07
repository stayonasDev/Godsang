package godsang.backend.repository.search;

import godsang.backend.entity.dto.FoodDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodRepositoryCustom {
    Page<FoodDto> searchFoodDto(Pageable pageable);

    Page<FoodDto> searchOneFoodName(Pageable pageable, String foodName);
}
