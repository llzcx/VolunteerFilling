package com.social.demo.manager.security.config;


import com.social.demo.manager.security.authentication.JwtAuthenticationFilter;
import com.social.demo.manager.security.authorize.CustomSecurityMetadataSource;
import com.social.demo.manager.security.authorize.DynamicSecurityFilter;
import com.social.demo.manager.security.authorize.MyAccessDecisionManager;
import com.social.demo.manager.security.exception.JwtAccessDeniedHandler;
import com.social.demo.manager.security.exception.JwtAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


/**
 * SpringSecurity3.0配置类
 * @author 陈翔
 */
@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class MySecurityConfig {

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize-> {
           log.info("放行路径：{}",Arrays.toString(PathConfig.RELEASABLE_PATH));
                    try {
                        authorize
                                // 需要放行的接口
                                .requestMatchers(PathConfig.RELEASABLE_PATH).permitAll()
                                // 其余的都需要权限校验
                                .anyRequest().authenticated()
                                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                                    @Override
                                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                                        object.setSecurityMetadataSource(customSecurityMetadataSource());
                                        object.setAccessDecisionManager(myAccessDecisionManager());
                                        return object;
                                    }
                                }).and()
                                // 防跨站请求伪造
                                .csrf().disable()
                                .cors().and()
                                .cors().configurationSource(corsConfigurationSource()).and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                                .exceptionHandling()
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler).and()
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .addFilterAt(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

        ).build();
    }
    /**
     * 基于用户名和密码或使用用户名和密码进行身份验证
     * @param config
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 提供编码机制
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置跨源访问(CORS)
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 获取访问url所需要的角色信息
     */
    @Bean
    public CustomSecurityMetadataSource customSecurityMetadataSource(){
        return new CustomSecurityMetadataSource();
    };
    /**
     * 认证权限处理 - 将可以请求URL的角色权限与当前登录用户的角色做对比，如果包含其中一个角色即可正常访问
     */
    @Bean
    public MyAccessDecisionManager myAccessDecisionManager(){
        return new MyAccessDecisionManager();
    };

    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }

}