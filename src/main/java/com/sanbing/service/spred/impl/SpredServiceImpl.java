package com.sanbing.service.spred.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sanbing.common.constant.Constant;
import com.sanbing.common.utils.Dingding;
import com.sanbing.service.spred.SpredService;
import com.taobao.api.ApiException;
import constant.constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Slf4j
@Service
public class SpredServiceImpl implements SpredService {

    @Value("${zb_bina}")
    private double zb_bina;

    @Override
    public JSONArray getSpred() {
        JSONArray result = new JSONArray();

        try {
            //zb交易对
            for (Map.Entry<String, String> entry : constant.ZB_CONTRACT_PRICE_MAP.entrySet()) {
                String symbol = entry.getKey();
                //bina交易对
                for (Map.Entry<String, String> entry2 : Constant.BINA_CONTRACT_PRICE_MAP.entrySet()) {
                    if ((symbol.split("_")[0]).equals(entry2.getKey().split("USDT")[0])) {
                        JSONObject json = new JSONObject();
                        double zb_price = Double.valueOf(entry.getValue());
                        double bina_price = Double.valueOf(entry2.getValue());
                        double spred = ((zb_price - bina_price) / bina_price) * 1000;
                        DecimalFormat df = new DecimalFormat("0.00");
                        json.put("symbol", symbol);
                        json.put("zb_price", zb_price);
                        json.put("bina_price", bina_price);
                        json.put("spred", df.format(spred));
                        result.add(json);
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return jsonArraySort(result);
    }

    @Scheduled(fixedRate = 10 * 60 * 1000)
    @Override
    public void sendMessage() {
        String message = "";
        JSONArray data = getSpred();
        for (int i = 0; i < data.size(); i++) {
            JSONObject json = data.getJSONObject(i);
            String symbol = json.getString("symbol");
            double zb_price = json.getDoubleValue("zb_price");
            double bina_price = json.getDoubleValue("bina_price");
            double spred = json.getDoubleValue("spred");
            if (Math.abs(spred) > zb_bina) {
                message = message + "交易对：" + symbol + "\n中币价格：" + zb_price + "\n币安价格: " + bina_price + "\n差价：" + spred + "‰\n\n";

            }
        }
        if (!message.isEmpty()) {
            try {
                Dingding.sendMessage(message);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }


    /*
    jsonArray按照字段排序
     */
    public static JSONArray jsonArraySort(JSONArray jsonArray) {

        //存放排序后的jsonArray
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> list = new ArrayList<>();
        list = JSONObject.parseArray(jsonArray.toJSONString(), JSONObject.class);

        Collections.sort(list, new Comparator<JSONObject>() {
            //排序字段
            private static final String key = "spred";

            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                double valueA = 0;
                double valueB = 0;
                try {
                    valueA = o1.getDoubleValue(key);
                    valueB = o2.getDoubleValue(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return Double.compare(Math.abs(valueB), Math.abs(valueA));
            }
        });
        sortedJsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        return sortedJsonArray;
    }
}
