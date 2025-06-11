//package godsang.backend.repository;
//
//import godsang.backend.entity.member.Member;
//import godsang.backend.entity.member.exception.MemberExceptions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@SpringBootTest
//class MemberRepositoryTest {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public MemberRepositoryTest(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
//        this.memberRepository = memberRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Test
//    public void testInsert() {
//
//        for (int i = 1; i <= 100; i++) {
//            Member member = Member.builder()
//                    .userId("user" + i)
//                    .password(passwordEncoder.encode("1111"))
//                    .name("USER" + i)
//                    .email("user" + i + "@aaa.com")
//                    .role(i <= 80 ? "USER" : "ADMIN")
//                    .build();
//
//            memberRepository.save(member);
//        }
//    }
//
//    @Test
//    @DisplayName("회원 조회")
//    void testRead(){
//        Long id = 2L;
//
//        Optional<Member> result = memberRepository.findById(id);
//
//        Member member =
//                result.orElseThrow(MemberExceptions.NOT_FOUND::get);
//        System.out.println(member);
//    }
//
//    @Test
//    @DisplayName("회원 수정")
//    @Transactional
//    @Commit
//    void testUpdate() {
//        Long id = 2L;
//
//        Optional<Member> result = memberRepository.findById(id);
//
//        Member member =
//                result.orElseThrow(MemberExceptions.NOT_FOUND::get);
//
//        member.changePassword(passwordEncoder.encode("2222"));
//        member.changeName("USER-1");
//    }
//
//    @Test
//    @DisplayName("회원 삭제")
//    @Commit
//    public void testDelete(){
//        Long id = 1L;
//        Optional<Member> result = memberRepository.findById(id);
//
//        Member member =
//                result.orElseThrow(() -> MemberExceptions.NOT_FOUND.get());
//
//        memberRepository.delete(member);
//    }
//}