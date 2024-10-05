package com.dastro.finance.finance_manager.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {
    public static Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);

        cookie.setMaxAge(maxAge);
        cookie.setPath("/"); // 모든 경로에 대해 유효

        return cookie;
    }
}
