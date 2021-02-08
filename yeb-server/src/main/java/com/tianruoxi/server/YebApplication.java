package com.tianruoxi.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author：Tianruoxi
 * @date：2021/2/8 16:06
 */

@SpringBootApplication
@MapperScan("com.tianruoxi.server.mapper")
public class YebApplication {
  public static void main(String[] args) {
    SpringApplication.run(YebApplication.class, args);
  }
}
