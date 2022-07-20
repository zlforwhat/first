package com.sanbing.common.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;

public class HttpClientUtil {
    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String doPost(HttpClient httpsClient, String url, Map<String, String> paramHeader,
                                Map<String, String> paramBody) throws Exception {
        return doPost(httpsClient, url, paramHeader, paramBody, DEFAULT_CHARSET);
    }

    public static String doPost(HttpClient httpsClient, String url, Map<String, String> paramHeader,
                                Map<String, String> paramBody, String charset) throws Exception {

        String result = null;
        HttpPost httpPost = new HttpPost(url);
        setHeader(httpPost, paramHeader);
        setBody(httpPost, paramBody, charset);

        HttpResponse response = httpsClient.execute(httpPost);
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, charset);
            }
        }

        return result;
    }

    public static String doGet(HttpClient httpsClient, String url, Map<String, String> paramHeader,
                               Map<String, String> paramBody) throws Exception {
        return doGet(httpsClient, url, paramHeader, paramBody, DEFAULT_CHARSET);
    }

    public static String doGet(HttpClient httpsClient, String url, Map<String, String> paramHeader,
                               Map<String, String> paramBody, String charset) throws Exception {

        String result = null;
        URIBuilder uriBuilder = new URIBuilder(url);
        List<NameValuePair> list = new LinkedList<>();
        for(Map.Entry<String, String> entry:paramBody.entrySet()){
            BasicNameValuePair param = new BasicNameValuePair(entry.getKey(),entry.getValue());
            list.add(param);
        }
        uriBuilder.setParameters(list);
        // 根据带参数的URI对象构建GET请求对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        setHeader(httpGet, paramHeader);
        HttpResponse response = httpsClient.execute(httpGet);
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, charset);
            }
        }

        return result;
    }

    private static void setHeader(HttpRequestBase request, Map<String, String> paramHeader) {
        // 设置Header
        if (paramHeader != null) {
            Set<String> keySet = paramHeader.keySet();
            for (String key : keySet) {
                request.addHeader(key, paramHeader.get(key));
            }
        }
    }

    private static void setBody(HttpPost httpPost, Map<String, String> paramBody, String charset) throws Exception {
        // 设置参数
        if (paramBody != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Set<String> keySet = paramBody.keySet();
            for (String key : keySet) {
                list.add(new BasicNameValuePair(key, paramBody.get(key)));
            }

            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
        }
    }
}
