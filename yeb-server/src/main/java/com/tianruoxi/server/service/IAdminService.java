package com.tianruoxi.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianruoxi.server.pojo.Admin;
import com.tianruoxi.server.pojo.RespBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Tianruoxi
 * @since 2021-02-05
 */

public interface IAdminService extends IService<Admin> {

  // 登录之后返回token
  RespBean login(String username, String password, String code, HttpServletRequest request);

  // 根据用户名获取用户
  Admin getAdminByUserName(String username);
}
