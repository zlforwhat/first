package com.sanbing.service.websocket;

import com.alibaba.fastjson.JSONObject;
import com.sanbing.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

@Slf4j
public class WsWebsocket extends WebSocketClient {

    public WsWebsocket(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info(" websocket open");
        JSONObject object = new JSONObject();
        object.put("op", "ping");
        send(object.toJSONString());
    }

    @Override
    public void onMessage(String s) {
//        log.info(s);
        JSONObject json = JSONObject.parseObject(s);
        String pong = json.getString("type");
        if(pong !=null && pong.equals("pong")){
            doSub();
        }
        String channel = json.getString("channel");
        if(pong !=null && pong.equals("update") && channel !=null && channel.equals("trades")){
           JSONObject result = json.getJSONArray("data").getJSONObject(0);
            Constant.FTX_CONTRACT_PRICE_MAP.put(json.getString("market"), result.getString("price"));
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info(" websocket close");
        log.info(s);
    }

    @Override
    public void onError(Exception e) {
        log.error("websocket error {}", e);
    }


    private void doSub(){
        JSONObject sub = new JSONObject();
        sub.put("op", "subscribe");
        sub.put("channel", "trades");
        //订阅所有ftx交易对
        for (int i = 0; i < Constant.FTX_SYMBOLS.size(); i++) {
            sub.put("market", Constant.FTX_SYMBOLS.get(i));
            send(sub.toJSONString());
        }
    }
}
