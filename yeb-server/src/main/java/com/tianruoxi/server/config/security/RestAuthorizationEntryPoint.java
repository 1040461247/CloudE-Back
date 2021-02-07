package com.tianruoxi.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianruoxi.server.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author：Tianruoxi
 * @date：2021/2/7 14:36
 * 当未登录或者token时效的时候访问接口时，自定义的返回结果。
 */

@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    RespBean bean = RespBean.error("尚未登陆，请登录！");
    bean.setCode(401);
    out.write(new ObjectMapper().writeValueAsString(bean));
    out.flush();
    out.close();
  }
}
