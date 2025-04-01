package godsang.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "intake_history")
public class IntakeHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "meal_type")
    private MealType mealType;

    @Column(name = "intake_date")
    private LocalDateTime intakeDate;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<IntakeFood> intakeFoods = new ArrayList<>();

    public void addIntakeFood(IntakeFood intakeFood) {
        intakeFoods.add(intakeFood);
        intakeFood.addIntakeHistory(this);
    }
}
