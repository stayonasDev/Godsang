package godsang.backend.repository;

import godsang.backend.entity.Food;
import godsang.backend.repository.search.FoodRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {
    public Optional<Food> findFirstByNameContaining(String foodName);
}
