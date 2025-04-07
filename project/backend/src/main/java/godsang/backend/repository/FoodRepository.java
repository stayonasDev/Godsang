package godsang.backend.repository;

import godsang.backend.entity.Food;
import godsang.backend.entity.dto.FoodDto;
import godsang.backend.repository.search.FoodRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {
    public Optional<Food> findFirstByNameContaining(String foodName);
}
