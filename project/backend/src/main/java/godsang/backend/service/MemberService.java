//package godsang.backend.service;
//
//import godsang.backend.entity.member.Member;
//import godsang.backend.entity.member.MemberDTO;
//import godsang.backend.entity.member.exception.MemberExceptions;
//import godsang.backend.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Log4j2
//@Transactional
//public class MemberService {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public MemberDTO read(String userId, String password) {
//        Optional<Member> result = memberRepository.findByUserId(userId);
//
//        Member member = result.orElseThrow(MemberExceptions.NOT_FOUND::get);
//        log.info("Member = {}", member);
//        if (!passwordEncoder.matches(password, member.getPassword())) {
//            throw MemberExceptions.BAD_CREDENTIALS.get();
//        }
//        return new MemberDTO(member);
//    }
//}
