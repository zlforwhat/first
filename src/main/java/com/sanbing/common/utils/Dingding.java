package com.sanbing.common.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


//钉钉发送消息
@Slf4j
@Component
public class Dingding implements InitializingBean {

    private static String webhook;

    @Value("${Webhook}")
    private String getWebhook;

    @Override
    public void afterPropertiesSet() throws Exception {
        Dingding.webhook = this.getWebhook;
    }

    public static void sendMessage(String msg) throws ApiException {

        DingTalkClient client = new DefaultDingTalkClient(webhook);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(msg);
        request.setText(text);
        OapiRobotSendResponse response = client.execute(request);
//        System.out.println(response.getBody());
    }
}
