package godsang.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Food {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fo_id")
    private Long id;

    private String name;
    private int kal;
    private double carb;
    private double protein;
    private double pat;
    private double saturatedFat;
    private double transFat;
    private double polyFat;
    private double unsaturatedFat;
    private double sodium;
    private double cholest;
    private double sugar;
}
