package godsang.backend.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import godsang.backend.entity.Food;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
//Postman JSON 형식을 name이 못받음 getter로 지정함
public class FoodDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int kal;

    @NotNull
    private double carb;

    @NotNull
    private double protein;

    @NotNull
    private double pat;
    private double saturatedFat;
    private double transFat;
    private double polyFat;
    private double unsaturatedFat;
    private double sodium;
    private double cholest;
    private double sugar;

    public Food foodConversion() {
        return Food.builder().id(id).name(name).kal(kal).carb(carb).protein(protein)
                .pat(pat).saturatedFat(saturatedFat).transFat(transFat)
                .polyFat(polyFat).unsaturatedFat(unsaturatedFat)
                .sodium(sodium).cholest(cholest).sugar(sugar).build();
    }

    @QueryProjection
    public FoodDto(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.kal = food.getKal();
        this.carb = food.getCarb();
        this.protein = food.getProtein();
        this.pat = food.getPat();
        this.saturatedFat = food.getSaturatedFat();
        this.transFat = food.getTransFat();
        this.polyFat = food.getPolyFat();
        this.unsaturatedFat = food.getUnsaturatedFat();
        this.sodium = food.getSodium();
        this.cholest = food.getCholest();
        this.sugar = food.getSugar();
    }
}
