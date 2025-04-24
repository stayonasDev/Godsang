package godsang.backend.entity.dto;

import godsang.backend.entity.Food;
import godsang.backend.entity.MealType;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

//TODO
//Map<MealType, List<FoodList>> 사용할지 다른거 사용할지
public class IntakeResponseDto {
    private MealType mealType;
    private int amount;
    List<Food> foodList = new ArrayList<>();
}
