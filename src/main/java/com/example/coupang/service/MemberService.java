package com.example.coupang.service;

import com.example.coupang.dto.MemberDTO;
import com.example.coupang.entity.MemberEntity;
import com.example.coupang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service        // Bean 으로 등록, 의존성
@RequiredArgsConstructor    // final field 생성자 자동생성 ( LOMBOK )
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);    // repository 의 save 를 호출(extends JpaRepository) db에 저장.
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byEmail = memberRepository.findByEmail(memberDTO.getEmail());    // email 로 회원정보 조회 (Optional 로 감싸진 결과 반환하자.)

        if (byEmail.isPresent()) {
            MemberEntity memberEntity = byEmail.get();  // if - true  -> Optional 에서 객체 소환

            if (memberEntity.getPassword().equals(memberDTO.getPassword())) {     // memberEntity - memberDTO equal password
                return MemberDTO.toMemberDTO(memberEntity);     // MemberDTO 를 memberEntity 로 변환해서 리턴.
            } else {    // 비밀번호 틀리면
                return null;    // 실패
            }
        } else {    // 이메일 틀리면
            return null;    // 실패
        }
    }
}