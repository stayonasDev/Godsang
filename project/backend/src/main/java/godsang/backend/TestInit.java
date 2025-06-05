//package godsang.backend;
//
//import godsang.backend.entity.Food;
//import godsang.backend.entity.member.Member;
//import godsang.backend.entity.dto.FoodDto;
//import godsang.backend.repository.MemberRepository;
//import godsang.backend.service.FoodService;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class TestInit implements CommandLineRunner {
//
//        private final FoodService foodService;
//        private final MemberRepository memberRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        for (int i = 0; i < 100; i++) {
//            foodService.foodRegister( new FoodDto(null, "음식" + i, 100, 20, 20, 20,
//                    0, 0, 0, 0, 1, 1, 1));
//        }
//
//        for (int i = 0; i < 100; i++) {
//            foodService.foodRegister( new FoodDto(null, "치킨" + i, 100, 20, 20, 20,
//                    0, 0, 0, 0, 1, 1, 1));
//        }
//
//        for (int i = 0; i < 50; i++) {
//            memberRepository.save(new Member(null, "memberA" + i, "pass", "A@A"
//                    , "010-1111-1111", "userA"));
//        }
//        }
//}
