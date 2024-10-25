package com.dastro.finance.finance_manager.service;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

import com.dastro.finance.finance_manager.entity.Member;

import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {
    
    public Optional<Member> getMemberInfo(Long userId, String passwd);

    public void loginCheckAndInsertModel(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request, Model model);

}
