package com.heyuan.dine_space_reserve;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.heyuan.dine_space_reserve.mapper")
public class DineSpaceReserveApplication {

  public static void main(String[] args) {
    SpringApplication.run(DineSpaceReserveApplication.class, args);
  }
}
