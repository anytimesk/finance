package com.dastro.finance.finance_manager.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dastro.finance.finance_manager.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    public Member findByIdAndPasswd(String id, String passwd);

    public Member findByEmail(String email);
}
