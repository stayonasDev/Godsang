package godsang.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "intake_food")
public class IntakeFood {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "in_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private IntakeHistory intakeHistory;

    private int amount;

    public void addIntakeHistory(IntakeHistory intakeHistory) {
        this.intakeHistory = intakeHistory;
    }
}
