package com.fzu.config;

import com.fzu.filter.JwtFilter;
import com.fzu.filter.JwtLoginFilter;
import com.fzu.service.impl.AdminServiceImpl;
import com.fzu.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/*").permitAll()
                .antMatchers("/user/**").hasAuthority("admin");
        http.formLogin()
//                .loginPage("/tologin")
                .usernameParameter("loginname")
                .passwordParameter("password")
                .loginProcessingUrl("/login");
        http.csrf().disable();
        http.addFilterBefore(new JwtLoginFilter("/login", authenticationManager(), redisTemplate),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(authenticationManager(), tokenManager, redisTemplate), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

}
