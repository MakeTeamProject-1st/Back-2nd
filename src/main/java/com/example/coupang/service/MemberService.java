package com.example.coupang.service;

import com.example.coupang.dto.MemberDTO;
import com.example.coupang.entity.MemberEntity;
import com.example.coupang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service    // Bean 으로 등록, 의존성
@RequiredArgsConstructor    // final field 생성자 자동생성 ( LOMBOK )
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입 처리
    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity); // repository 의 save 를 호출(extends JpaRepository) db에 저장.
    }

    // 로그인 처리
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byEmail = memberRepository.findByEmail(memberDTO.getEmail());

        if (byEmail.isPresent()) {
            MemberEntity memberEntity = byEmail.get();  // if - true  -> Optional 에서 객체 소환
            if (memberEntity.getPassword().equals(memberDTO.getPassword())) {   // memberEntity - memberDTO equal password
                return MemberDTO.toMemberDTO(memberEntity); // MemberDTO 를 memberEntity 로 변환해서 리턴.
            }
        }
        return null; // 로그인 실패
    }

    // 회원탈퇴 처리 . 성공과 실패 뿐이니 boolean 진행.
    public boolean deleteMemberByEmailAndPassword(String email, String password) {
        Optional<MemberEntity> memberEntity = memberRepository.findByEmail(email);

        if (memberEntity.isPresent()) {
            MemberEntity entity = memberEntity.get();
            if (entity.getPassword().equals(password)) {
                memberRepository.delete(entity); // 회원 삭제
                return true;
            }
        }
        return false; // 비밀번호 불일치, 이메일 없음 등
    }
}
