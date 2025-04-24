package godsang.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "intake_food")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "amount"})
@AllArgsConstructor
public class IntakeFood {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intake_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private IntakeHistory intakeHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    private int amount;

    public void addIntakeHistory(IntakeHistory intakeHistory) {
        this.intakeHistory = intakeHistory;
    }
}
