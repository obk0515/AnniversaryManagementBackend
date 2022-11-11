package com.fzu.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzu.entity.Admin;
import com.fzu.utils.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    private RedisTemplate redisTemplate;

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
        setAuthenticationManager(authenticationManager);
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return super.requiresAuthentication(request, response);
    }

    /**
     * 从登录参数json数据中获取用户名密码，然后调用AuthenticationManager.authenticate()方法进行校验。
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException, IOException {
        //将用户传的json数据转为user对象
        Admin admin = new Admin();
        try {
            admin = new ObjectMapper().readValue(req.getInputStream(), Admin.class);
        } catch (IOException e) {
            String jwt = req.getHeader("access-token");
            try {
                String subject = JwtUtil.parseJWT(jwt).getSubject();
                String loginname = subject.substring(subject.indexOf("loginname=") + 10);
                loginname = loginname.substring(0, loginname.indexOf(","));
                String password = subject.substring(subject.indexOf("password=") + 9);
                password = password.substring(0, password.indexOf(","));
                admin.setLoginname(loginname);
                admin.setPassword(password);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword()));
    }

    /**
     * 校验成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        StringBuffer sb = new StringBuffer();
        for (GrantedAuthority authority : authorities) {
            sb.append(authority.getAuthority()).append(",");
        }
        String jwt = Jwts.builder()
                .claim("authorities", sb)
                .setSubject(String.valueOf(authResult.getPrincipal()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24 * 7))//设置过期时间
                .signWith(SignatureAlgorithm.HS512, "ruangong")//设置加密方式，以及key
                .compact();
        //设置登录成功后返回的信息
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        map.put("msg", "登录成功");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(map));
        writer.flush();
        writer.close();
        Admin admin = (Admin) authResult.getPrincipal();
        redisTemplate.opsForValue().set(admin.getUsername(), "admin");
    }

    /**
     * 校验失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> map = new HashMap<>();
        map.put("msg", "登录失败");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(map));
        writer.flush();
        writer.close();
    }

}
