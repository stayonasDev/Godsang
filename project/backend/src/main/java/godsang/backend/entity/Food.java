package godsang.backend.entity;

import godsang.backend.entity.dto.FoodDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Food {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
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

    public void foodUpdate(FoodDto dto) {
        this.name = dto.getName();
        this.kal = dto.getKal();
        this.carb = dto.getCarb();
        this.protein = dto.getProtein();
        this.pat = dto.getPat();
        this.saturatedFat = dto.getSaturatedFat();
        this.transFat = dto.getTransFat();
        this.polyFat = dto.getPolyFat();
        this.unsaturatedFat = dto.getUnsaturatedFat();
        this.sodium = dto.getSodium();
        this.cholest = dto.getCholest();
        this.sugar = dto.getSugar();
    }

}
