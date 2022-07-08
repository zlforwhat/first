package com.sanbing.service.spred.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sanbing.common.constant.Constant;
import com.sanbing.service.spred.SpredService;
import constant.constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Slf4j
@Service
public class SpredServiceImpl implements SpredService {

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

    /*
    jsonArray按照字段排序
     */
    public static JSONArray jsonArraySort(JSONArray jsonArray){

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

                return Double.compare(Math.abs(valueB) ,Math.abs(valueA));
            }
        });
        sortedJsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        return sortedJsonArray;
    }
}
