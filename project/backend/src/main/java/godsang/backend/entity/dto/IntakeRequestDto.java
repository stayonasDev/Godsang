package godsang.backend.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import godsang.backend.entity.MealType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//Date, 아침 식사, food_ids, member_id,
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IntakeRequestDto {
    private Long memberId;

    @NotNull
//    @DateTimeFormat()
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate date;

    @NotNull
    private MealType mealType;

    @NotNull
    private List<RequestItem> items;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestItem{
        private Long fooId;
        private int gram;
    }
}
