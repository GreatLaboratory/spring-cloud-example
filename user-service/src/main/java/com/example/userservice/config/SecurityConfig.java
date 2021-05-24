package com.example.userservice.config;

import com.example.userservice.filter.AuthenticationFilter;
import com.example.userservice.service.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final UsersServiceImpl usersService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 로그인에 대한 엔드포인트 설정이 따로 없다면 /login이 된다.
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/actuator/**").permitAll()
                    .antMatchers("/**")
                        .hasIpAddress(env.getProperty("gateway.ip"))
                .and()
                    .addFilter(getAuthenticationFilter());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // usersService는 UserDetailsService를 상속받았기에 loadUserByUsername메소드를 구현해야하고 이 메소드를 통해 요청으로 받았던 email을 db에 조회하여 존재하는 유저인지 load한다.
        // 여기소 요청으로 받은 email은 위 메소드에서 getAuthenticationFilter()를 통해 받아와진다.
        // 로그인할 때 사용자로부터 받은 암호화되지않은 pwd값을 암호화시켜서 db에 저장되어있는 암호화된 encryptedPwd와 비교하기 위함.
        auth.userDetailsService(usersService).passwordEncoder(passwordEncoder);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        return new AuthenticationFilter(env, usersService, authenticationManager());
    }

}
