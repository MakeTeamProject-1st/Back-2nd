package com.example.coupang.dto;

import com.example.coupang.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String email;
    private String phoneNum;
    private String password;
    private String address;
    private boolean gender;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setPhoneNum(memberEntity.getPhoneNum());
        memberDTO.setPassword(memberEntity.getPassword());
        memberDTO.setAddress(memberEntity.getAddress());
        memberDTO.setGender("men".equalsIgnoreCase(memberEntity.getGender()));
        return memberDTO;
    }
}

