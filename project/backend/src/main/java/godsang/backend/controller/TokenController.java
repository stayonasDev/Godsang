package godsang.backend.controller;

import godsang.backend.entity.member.MemberDTO;
import godsang.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/godsang/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {

    private final MemberService memberService;

    @PostMapping("/make")
    public ResponseEntity<Map<String, String>> mapToken(@RequestBody MemberDTO memberDTO) {
        log.info("make token............................");

        MemberDTO memberDTOResult = memberService.read(memberDTO.getUserId(), memberDTO.getPassword());
        log.info(memberDTOResult);
        return ResponseEntity.status(200).body(Map.of("Success", "Complete"));
    }
}
