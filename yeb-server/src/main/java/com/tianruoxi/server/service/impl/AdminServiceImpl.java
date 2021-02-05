package com.tianruoxi.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tianruoxi.server.config.security.JwtTokenUtil;
import com.tianruoxi.server.mapper.AdminMapper;
import com.tianruoxi.server.pojo.Admin;
import com.tianruoxi.server.pojo.RespBean;
import com.tianruoxi.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Tianruoxi
 * @since 2021-02-05
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Value("${jwt.tokenHead}")
  private String tokenHead;
  @Autowired
  AdminMapper adminMapper;

  // 登录之后返回token
  @Override
  public RespBean login(String username, String password, HttpServletRequest request) {
    // 登录
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (null == userDetails || passwordEncoder.matches(password, userDetails.getPassword())) {
      return RespBean.error("用户名或密码错误");
    }
    if(!userDetails.isEnabled()) {
      return RespBean.error("账号被禁用，请联系管理员！");
    }

    // 更新security登陆用户对象，放入全局
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    // 生成token
    String token = jwtTokenUtil.generateToken(userDetails);
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);

    return RespBean.success("登陆成功", tokenMap);
  }

  // 根据用户名获取用户
  @Override
  public Admin getAdminByUserName(String username) {
    return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
  }
}
