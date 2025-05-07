package godsang.backend.entity.dto;

import godsang.backend.entity.MealType;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
 public class IntakeResponseDto {
    private Map<MealType, List<FoodDto>> intakeMap;

    public IntakeResponseDto() {
        this.intakeMap = new HashMap<>();
        this.intakeMap.put(MealType.MORNING ,new ArrayList<>());
        this.intakeMap.put(MealType.LUNCH ,new ArrayList<>());
        this.intakeMap.put(MealType.DINNER ,new ArrayList<>());
    }
}
