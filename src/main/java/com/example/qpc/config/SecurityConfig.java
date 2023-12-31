package com.example.qpc.config;

import com.example.qpc.config.customError.CustomAccessDeniedHandler;
import com.example.qpc.config.customError.CustomAuthenticationEntryPoint;
import com.example.qpc.config.customError.CustomMethodNotAllowedFilter;
import com.example.qpc.handler.CustomLogoutHandler;
import com.example.qpc.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected MemberService memberService;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public CustomLogoutHandler myCustomLogoutHandler() {
        return new CustomLogoutHandler(memberService);
    }


    // 첫 잠금 해제
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // post , put 공격 방어
        http
                .csrf().disable()
                // 이 주소를 받는 곳에만 모든곳에서 접근가능하도록 설정
                .authorizeRequests()
                .antMatchers("/member/save", "/member/login", "/member/login/error", "/memberSave/mailConfirm", "/test", "/index").anonymous()
                .antMatchers("/member/findById/**").anonymous()
                .antMatchers("/logout").permitAll()
                .antMatchers("/category/**").hasRole("ADMIN")
                .antMatchers("/chat/**").permitAll()
                .antMatchers("/GameCategory/**").hasRole("ADMIN")
                .antMatchers("/game/**").hasRole("ADMIN")
                .antMatchers("/product/**").hasRole("ADMIN")
                .antMatchers("/payment/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/member/**").permitAll()
                .antMatchers("/member/**").hasRole("MEMBER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/error/**").permitAll()
                .antMatchers(HttpMethod.POST, "/payment/**").permitAll()
                .antMatchers(HttpMethod.POST, "/member/login").anonymous()
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                /* 로그인페이지 설정, 위에 설정된 주소 말고 다른 곳으로 갈때
                로그인되어 있지 않다면 redirect로 로그인창으로 */
                // 로그인 페이지 URL 설정
                .formLogin()
                .loginPage("/index")                 // HTML명
                .usernameParameter("memberId")
                .passwordParameter("memberPassword")
                .defaultSuccessUrl("/memberPages/memberMain")
                .failureUrl("/member/login/error")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler) // 커스텀 AccessDeniedHandler 사용
                .authenticationEntryPoint(authenticationEntryPoint) // 커스텀 AuthenticationEntryPoint 사용
                .and()
                .addFilterAfter(new CustomMethodNotAllowedFilter(), ExceptionTranslationFilter.class) // 커스텀 MethodNotAllowedFilter 사용
                .logout()
                .logoutSuccessUrl("/")
                .addLogoutHandler(myCustomLogoutHandler()) // 커스텀 로그아웃 핸들러 등록
                .invalidateHttpSession(true)
                .permitAll();
    }

    // 패스워드 암호화(인코딩)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    // 인증을 수행하기 위한 인터페이스
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // CSS, JS, IMG 등 정적 리소스에 대한 인증 및 권한 부여 필터를 건너뛰게한다.
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
