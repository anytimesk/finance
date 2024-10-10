package com.dastro.finance.finance_manager.service;

import java.util.Optional;

import com.dastro.finance.finance_manager.entity.Member;

public interface MemberService {
    
    public Optional<Member> getMemberInfo(Long userId, String passwd);

}
