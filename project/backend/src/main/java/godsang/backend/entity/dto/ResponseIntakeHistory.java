package godsang.backend.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ResponseIntakeHistory {
    private int amount;
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
