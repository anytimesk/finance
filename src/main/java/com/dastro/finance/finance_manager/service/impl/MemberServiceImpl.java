package com.dastro.finance.finance_manager.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dastro.finance.finance_manager.entity.Member;
import com.dastro.finance.finance_manager.repo.MemberRepository;
import com.dastro.finance.finance_manager.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Optional<Member> getMemberInfo(Long userId, String passwd) {
        Optional<Member> member = memberRepository.findByIdAndPasswd(userId, passwd);

        return member;
    }
}
