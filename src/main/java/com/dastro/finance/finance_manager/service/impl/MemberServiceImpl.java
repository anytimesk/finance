package com.dastro.finance.finance_manager.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.dastro.finance.finance_manager.entity.Member;
import com.dastro.finance.finance_manager.repo.MemberRepository;
import com.dastro.finance.finance_manager.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepo;

    @Override
    public Optional<Member> getMemberInfo(Long userId, String passwd) {
        Optional<Member> member = memberRepo.findByIdAndPasswd(userId, passwd);

        return member;
    }

    public void loginCheckAndInsertModel(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request,
            Model model) {

        if (principal != null) {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("csrfToken", csrfToken.getToken());
            model.addAttribute("csrfParameterName", csrfToken.getParameterName());

            String name = principal.getAttribute("name"); // 사용자 이름 가져오기
            model.addAttribute("name", name);
            model.addAttribute("isLoggedIn", true); // 로그인 상태
        } else {
            model.addAttribute("isLoggedIn", false); // 로그인 상태
        }
    }

    public List<Member> getAllUsers() {
        return memberRepo.findAll();
    }
}
