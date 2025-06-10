package godsang.backend.controller;

import godsang.backend.entity.IntakeHistory;
import godsang.backend.entity.MealType;
import godsang.backend.entity.dto.*;
import godsang.backend.service.FoodService;
import godsang.backend.service.IntakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@Tag(name = "Food And IntakeFoodCRUD")
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/godsang/food")
//@CrossOrigin(value = "http://localhost:5173")
@CrossOrigin(origins ="*")
public class FoodController {

    private final FoodService foodService;
    private final IntakeService intakeService;

    @Operation(summary = "음식 이름으로 검색", description = "ID 내림차순 결과로 페이징을 가져옴 최신순")
    @GetMapping("/foodList")
    public ResponseEntity<Page<FoodDto>> foodList(@RequestParam(name = "foodName", required = false)
                                                      String foodName, @Validated PageRequestDto pageRequestDto) {
        log.info("foodName = {}", foodName);
        return ResponseEntity.ok(foodService.foodList(foodName, pageRequestDto));
    }

    @Operation(summary = "음식을 추가합니다.")
    @PostMapping("/foods")
    public ResponseEntity<FoodDto> foodSave(@RequestBody @Validated FoodDto foodDto) {
        return ResponseEntity.ok(foodService.foodRegister(foodDto));
    }

    @Operation(summary = "음식 정보를 수정합니다.", description = "Food 전체 데이터를 보내 수정")
    @PatchMapping("/foods")
    public ResponseEntity<FoodDto> foodEdit(@RequestBody FoodDto foodDto) {
        return ResponseEntity.ok().body(foodService.foodEdit(foodDto));
    }

//    @GetMapping("/intakes/{id}/{date}")
//    public ResponseEntity<Page<FoodDto>> intakesPage(@PathVariable(name = "id") Long id,
//                                                     @PathVariable(name = "date")LocalDate date, PageRequestDto page) {
//
//    }

    @Operation(summary = "섭취한 음식을 저장합니다.")
    @PostMapping("/intakes")
    public ResponseEntity<Map<String, String>> intakeSave(@RequestBody IntakeRequestDto request) {
        intakeService.registerIntakeFood(request);
        Map<String, String> result = Map.of("result", "success");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "해당 날짜를 조회해 아침, 점심, 저녁에 섭취한 음식들 amount에 맞게 반환합니다.")
    @GetMapping("/intakes")
    public ResponseEntity<IntakeResponseDto> intakeFoodOfDateList(@RequestParam(value = "memberId") Long memberId,
                                                                  @RequestParam(value = "date") LocalDate date) {
        RequestIIntakeHistory request = new RequestIIntakeHistory(memberId, date);
        return ResponseEntity.ok().body(intakeService.intakeHistoryList(request));
    }


    //MemberId, Date, MealType, FoodId
    @PatchMapping("/intakes")
    public ResponseEntity<?> intakeFoodEdit(@RequestParam Long MemberId, @RequestParam LocalDate date,
                               @RequestParam MealType meal, @RequestParam Long fooId) {
        //서비스 호출

        Map<Integer, String> response = Map.of(200, "Success");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/intakes")
    public void intakeFoodDelete() {

    }
}
