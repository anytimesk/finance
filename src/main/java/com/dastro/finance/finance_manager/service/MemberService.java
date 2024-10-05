package com.dastro.finance.finance_manager.service;

import com.dastro.finance.finance_manager.entity.Member;

public interface MemberService {
    
    public Member getMemberInfo(String userId, String passwd);

}
