package com.example.qpc.config.customError;

import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomMethodNotAllowedFilter extends org.springframework.web.filter.OncePerRequestFilter {

    @Override
    protected void doFilterInternal(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (org.springframework.web.HttpRequestMethodNotSupportedException ex) {
            // 405 에러가 발생한 경우 커스텀 에러 페이지로 리다이렉트
            redirectToErrorPage(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    private void redirectToErrorPage(HttpServletResponse response, int errorCode) throws IOException {
        response.sendRedirect("/error/" + errorCode); // 커스텀 405 에러 페이지로 리다이렉트
    }
}
