package godsang.backend.repository.search;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import godsang.backend.entity.Food;
import godsang.backend.entity.QFood;
import godsang.backend.entity.dto.FoodDto;
import godsang.backend.entity.dto.QFoodDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class FoodRepositoryCustomImpl implements FoodRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public FoodRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<FoodDto> searchFoodDto(Pageable pageable) {
        QFood food = QFood.food;

        List<FoodDto> fetch = queryFactory
                .select(new QFoodDto(food))
                .from(food)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(food.count())
                .from(food)
                .fetchOne();

        return new PageImpl<>(fetch, pageable, total);
    }

    @Override
    public Page<FoodDto> searchOneFoodName(Pageable pageable, String foodName) {
        QFood food = QFood.food;

        List<FoodDto> results = queryFactory
                .select(new QFoodDto(food))
                .from(food)
                .where(food.name.like("%" + foodName + "%"))
                .orderBy(food.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(food.count())
                .from(food)
                .where(food.name.like(foodName))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

//    private OrderSpecifier<?>[] getOrderBy(Pageable pageable, String foodName) {
//        if (foodName != null & !foodName.isEmpty()) {
//            QFood food = QFood.food;
//            return new OrderSpecifier<?>[]{
//                    food.name.asc()
//            };
//        }
//
//        return pageable.getSort().stream()
//                .map(order -> {
//                    QFood food = QFood.food;
//                    return order.isAscending()
//                            ? food.get
//                })
//    }
}
