package com.sanbing.service.spred;

import com.alibaba.fastjson.JSONArray;

public interface SpredService {

    JSONArray getSpred();

    JSONArray getZbBinaSpred();

    void sendMessage();


}
