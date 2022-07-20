package com.sanbing.controller.bina;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sanbing.common.utils.Dingding;
import com.sanbing.common.utils.HttpClientUtil;
import com.sanbing.service.websocket.WsWebsocket;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.java_websocket.WebSocket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("test2")
public class Test {


    @GetMapping("sendMessage")
    public void sendMessage() throws ApiException {
        Dingding.sendMessage("发送消息");
    }


}
