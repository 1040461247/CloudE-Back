package com.tianruoxi.server.config.security;

import com.tianruoxi.server.pojo.Admin;
import com.tianruoxi.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author：Tianruoxi
 * @date：2021/2/7 13:44
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private IAdminService adminService;
  @Autowired
  private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
  @Autowired
  private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
  }

  // 不走拦截链，放行路径
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(
      "/login",
      "/logout",
      "/css/**",
      "/js/**",
      "/index.html",
      "favicon.icon",
      "/doc.html",
      "/webjars/**",
      "/swagger-resources",
      "/v2/api-docs/**",
      "/captcha"
    );
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 使用jwt不需要csrf
    http.csrf()
      .disable()
      // 基于token，不需要session
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
      // 所有路径请求均要认证。
      .anyRequest().authenticated()
      .and()
      // 禁用缓存
      .headers()
      .cacheControl();

    // 添加jwt登录授权过滤器，当存在token但是用户并未登录时实行登录
    http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    // 添加自定义未授权和未登录结果返回，当token失效（未登录）或无访问接口的权限时执行
    http.exceptionHandling()
      .accessDeniedHandler(restfulAccessDeniedHandler)
      .authenticationEntryPoint(restAuthorizationEntryPoint);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    return username -> {
      Admin admin = adminService.getAdminByUserName(username);
      if(null != admin) {
        return admin;
      }
      return null;
    };
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter();
  }

}
