package godsang.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "intake_history")
@Builder
@ToString(of = {"id", "mealType", "intakeDate"})
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IntakeHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "meal_type")
    private MealType mealType;

    @Column(name = "intake_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate intakeDate;

    //@Builder.Default 필수
    @OneToMany(mappedBy = "intakeHistory", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private List<IntakeFood> intakeFoods = new ArrayList<>();

    public void addIntakeFood(IntakeFood intakeFood) {
        this.intakeFoods.add(intakeFood);
        intakeFood.addIntakeHistory(this);
    }
}
