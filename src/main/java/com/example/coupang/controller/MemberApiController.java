package com.example.coupang.controller;

import com.example.coupang.dto.MemberDTO;
import com.example.coupang.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService; // 서비스 계층 의존성 주입

    // 회원가입
    @PostMapping("/users")
    public ResponseEntity<String> save(@RequestBody MemberDTO memberDTO) {
        try {
            memberService.save(memberDTO);
            return ResponseEntity.ok("회원가입 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패");
        }
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);

        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getEmail()); // session에 로그인한 이메일 저장
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    // 로그아웃
    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 회원탈퇴
    @DeleteMapping("/users/resign")
    public ResponseEntity<String> deleteUser(@RequestParam String email, @RequestParam String password) {
        boolean isDeleted = memberService.deleteMemberByEmailAndPassword(email, password);

        if (isDeleted) {
            return ResponseEntity.ok("회원탈퇴 완료");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원탈퇴 실패");
        }
    }
}
