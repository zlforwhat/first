package com.sanbing.controller.bina;

import com.sanbing.common.utils.Dingding;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("test2")
public class Test {

    @GetMapping("sendMessage")
    public void sendMessage() throws ApiException {
        Dingding.sendMessage("发送消息");

    }
}
