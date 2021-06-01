package com.example.userservice.filter;

import com.example.userservice.dto.login.UserLoginRequestDto;
import com.example.userservice.dto.read.UserReadResponseDto;
import com.example.userservice.service.UsersServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.netflix.appinfo.EurekaAccept.compact;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Environment env;
    private final UsersServiceImpl usersService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 로그인 과정의 가장 첫번째 단계이다. 클라로부터 request body로 들어온 객체를 dto로 변환 후 email과 pwd를 얻고
            // 둘을 비교하여 맞는 계정인지를 판단한다.
            UserLoginRequestDto creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPwd()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 위 메소드에서 맞는 계정이라 로그인이 성공하면 jwt를 발급한다.
        String userName = ((User)authResult.getPrincipal()).getUsername();
        UserReadResponseDto userDetails = usersService.getUserByEmail(userName);

        log.info("@@@@@@@@@@@@@@@@@@@user-service jwt ->" + env.getProperty("token.secret"));
        String token = Jwts.builder()
                .setSubject(userDetails.getUserId()) // jwt에 들어갈 주인이 userId가 된다.
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time")))) // 만료일 설정
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")) // 서명하게 될 비밀번호
                .compact();

        response.addHeader("token", token); // /login 엔드포인트가 성공하여 200 ok를 응답할 때 header에 jwt token값을 준다.
        response.addHeader("userId", userDetails.getUserId());
    }


}
