package com.tianruoxi.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：Tianruoxi
 * @date：2021/2/8 16:05
 * 测试Swagger接口
 */

@RestController
public class HelloController {

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

}
