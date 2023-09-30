package com.heyuan.dine_space_reserve.controller;

import com.heyuan.dine_space_reserve.common.Resp;
import com.heyuan.dine_space_reserve.service.client.WechatHttpClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
@Api(value = "WechatHttpController", tags = "微信调用控制器")
public class WechatHttpController {

  @Resource WechatHttpClient wechatHttpClient;

  @ApiOperation(value = "获取跳转二维码")
  @GetMapping("/QRCode")
  Resp<byte[]> getQRCode(@RequestParam String path, @RequestParam Double width) {
    return Resp.data(wechatHttpClient.getQRCode(path, width));
  }
}
