package com.security.test.securityconftest.member.data.repo;

import com.security.test.securityconftest.member.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepositroy extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    Optional<Member> findByUsername(String username);
}