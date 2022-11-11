package com.fzu.filter;

import com.fzu.utils.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtFilter extends BasicAuthenticationFilter {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public JwtFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        if (authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token != null) {
            String username = tokenManager.getUserInfoFromToken(token);
            username = username.substring(username.indexOf("loginname=") + 10);
            username = username.substring(0, username.indexOf(","));
            String permission = (String) redisTemplate.opsForValue().get(username);
            Collection<GrantedAuthority> authorityCollection = new ArrayList<>();
            SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permission);
            authorityCollection.add(auth);
            return new UsernamePasswordAuthenticationToken(username, token, authorityCollection);
        }
        return null;
    }
}