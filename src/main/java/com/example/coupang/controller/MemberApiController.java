package com.example.coupang.controller;

import com.example.coupang.dto.MemberDTO;
import com.example.coupang.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor    // final field 의 생성자 자동생성
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;      // 서비스계층에 의존성 주입해주자.

    @PostMapping("/users")
    public ResponseEntity<String> save(@RequestBody MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return ResponseEntity.ok("login");
    }   // 회원가입 완료

    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);     // service 처리 반환

        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getEmail());     // session 에 email 저장.
            return ResponseEntity.ok("로그인 성공");
            // login 완료
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");   // 인증실패시
        }
    }

    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();   // 세션 무효화하자.
        return ResponseEntity.ok("로그아웃 하였습니다.");
    }
}
