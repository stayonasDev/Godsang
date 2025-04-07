package godsang.backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

@Entity
@Table(name = "intake_food")
@Builder
@ToString(of = {"id", "amount"})
public class IntakeFood {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "in_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private IntakeHistory intakeHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fo_id")
    private Food food;

    private int amount;

    public void addIntakeHistory(IntakeHistory intakeHistory) {
        this.intakeHistory = intakeHistory;
    }
}
