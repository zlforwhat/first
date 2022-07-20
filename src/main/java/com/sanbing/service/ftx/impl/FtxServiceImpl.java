package com.sanbing.service.ftx.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sanbing.common.constant.Constant;
import com.sanbing.common.utils.HttpClientUtil;
import com.sanbing.service.ftx.FtxService;
import com.sanbing.service.websocket.WsWebsocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.java_websocket.WebSocket;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FtxServiceImpl implements FtxService {

    private static final String FUTURE_URL = "https://ftx.com/api/futures";

    private static final String SERVER_URL = "wss://ftx.com/ws/";


    @Override
    public void getSymbols() {
        HttpClient httpClient = null;
        httpClient = HttpClients.custom().disableAutomaticRetries().build();
        Map<String, String> paramBody = new HashMap<>();
        String result = null;

        try {
            result = HttpClientUtil.doGet(httpClient, FUTURE_URL, null, paramBody);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        if (result != null) {
            JSONObject json = JSONObject.parseObject(result);
            JSONArray array = json.getJSONArray("result");
            JSONArray symbols = new JSONArray();
            for (int i = 0; i < array.size(); i++) {
                String symbol = array.getJSONObject(i).getString("name");
                if (symbol.endsWith("-PERP") && !symbol.equals("DEFI-PERP")) {
                    Constant.FTX_SYMBOLS.add(symbol);
                }
            }
        }

    }

    @PostConstruct
    public void subFuture() {

        try {
            getSymbols();
            WsWebsocket wsWebsocket = new WsWebsocket(new URI(SERVER_URL));
            wsWebsocket.connect();
            while (!wsWebsocket.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("连接中。。。");
                Thread.sleep(1000);
            }
            // 连接成功往websocket服务端发送数据
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
