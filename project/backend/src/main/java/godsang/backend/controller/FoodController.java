package godsang.backend.controller;

import godsang.backend.entity.dto.FoodDto;
import godsang.backend.entity.dto.IntakeRequestDto;
import godsang.backend.entity.dto.PageRequestDto;
import godsang.backend.service.FoodService;
import godsang.backend.service.IntakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@Controller
@Tag(name = "Food And IntakeFoodCRUD")
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/godsang/food")
public class FoodController {

    private final FoodService foodService;
    private final IntakeService intakeService;

    @Operation(summary = "음식 이름으로 검색", description = "내림차순 결과로 페이징을 가져옴 최신순")
    @GetMapping("/foodList")
    public ResponseEntity<Page<FoodDto>> foodList(@RequestParam(name = "foodName", required = false)
                                                      String foodName, @Validated PageRequestDto pageRequestDto) {
        return ResponseEntity.ok(foodService.foodList(foodName, pageRequestDto));
    }

    @Operation(summary = "음식을 추가합니다.")
    @PostMapping("/save")
    public ResponseEntity<FoodDto> foodSave(@RequestBody FoodDto foodDto) {
        return ResponseEntity.ok(foodService.foodRegister(foodDto));
    }

    @Operation(summary = "섭취한 음식을 저장합니다.")
    @PostMapping("/intake")
    public ResponseEntity<?> intakeSave(@RequestBody IntakeRequestDto request) {
        intakeService.registerIntakeFood(request);
        return ResponseEntity.ok("ok");
    }
}
