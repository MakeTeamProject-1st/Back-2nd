package com.example.coupang.entity;

import com.example.coupang.dto.MemberDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "users")
public class MemberEntity {

    @Id
    @Column(unique = true, nullable = false, length = 100)
    private String email;       // 아마도 기본키? memberId 추가하면 수정하고.

    @Column(unique = true, nullable = false, length = 13)
    private String phoneNum;        // 중복가입방지?

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 10)
    private String gender;

    //TODO 생성시간 기록 ( 해도 되고 안해도 되고 나중에 보고 )
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setPhoneNum(memberDTO.getPhoneNum());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setAddress(memberDTO.getAddress());
        memberEntity.setGender(memberDTO.isGender() ? "men" : "women");
        return memberEntity;
    }
}