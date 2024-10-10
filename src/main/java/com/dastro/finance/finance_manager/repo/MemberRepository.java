package com.dastro.finance.finance_manager.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dastro.finance.finance_manager.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Object> {
    public Optional<Member> findByIdAndPasswd(Long id, String passwd);

    public Optional<Member> findByEmail(String email);
}
