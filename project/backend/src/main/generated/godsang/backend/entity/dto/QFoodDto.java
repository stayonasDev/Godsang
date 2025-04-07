package godsang.backend.entity.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * godsang.backend.entity.dto.QFoodDto is a Querydsl Projection type for FoodDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFoodDto extends ConstructorExpression<FoodDto> {

    private static final long serialVersionUID = -390907572L;

    public QFoodDto(com.querydsl.core.types.Expression<? extends godsang.backend.entity.Food> food) {
        super(FoodDto.class, new Class<?>[]{godsang.backend.entity.Food.class}, food);
    }

}

