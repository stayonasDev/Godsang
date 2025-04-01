package godsang.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fo_id")
    private Long id;

    private String name;
    private int kal;
    private double carb;
    private double protein;
    private double pat;
    private double sodium;
    private double cholest;
    private double sugar;
    private double gram;
}
