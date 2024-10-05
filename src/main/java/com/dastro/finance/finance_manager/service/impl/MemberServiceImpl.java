package com.dastro.finance.finance_manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dastro.finance.finance_manager.entity.Member;
import com.dastro.finance.finance_manager.repo.MemberRepository;
import com.dastro.finance.finance_manager.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberServiceImpl implements MemberService, UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member getMemberInfo(String userId, String passwd) {
        return memberRepository.findByIdAndPasswd(userId, passwd);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPasswd())
                .build();
    }
}
