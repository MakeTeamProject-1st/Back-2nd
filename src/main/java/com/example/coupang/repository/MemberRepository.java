package com.example.coupang.repository;

import com.example.coupang.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    Optional<MemberEntity> findByEmail(String email);
}
