package godsang.backend.repository;

import godsang.backend.entity.IntakeHistory;
import godsang.backend.entity.MealType;
import godsang.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IntakeHistoryRepository extends JpaRepository<IntakeHistory, Long> {

//    @Query("select ih from IntakeHistory ih where ih.intakeDate = :date and ih.member = :member")
//    public IntakeHistory findHistoryForDate(@Param("date") LocalDate date, @Param("member")Member member);

    @Query("select ih from IntakeHistory ih join fetch ih.intakeFoods where ih.intakeDate = :date and ih.member = :member")
    public List<IntakeHistory> findHistoryForDate(@Param("date") LocalDate date, @Param("member")Member member);

//    @Query("select ih from IntakeHistory fetch join ih.intakeFoods if ih where ih.member = :member and ih.intakeDate = :date " +
//            "and ih.mealType = :meal and if.food = :food")
//    public IntakeHistory findByIntakeHistory(@Param("member") Member member, @Param("date") LocalDate date,
//                                    @Param("meal")MealType meal);
}
