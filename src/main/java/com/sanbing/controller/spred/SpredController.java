package com.sanbing.controller.spred;

import com.alibaba.fastjson.JSONArray;
import com.sanbing.common.response.CodeEnum;
import com.sanbing.common.response.FormData;
import com.sanbing.common.response.ResponseResult;
import com.sanbing.service.spred.SpredService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("spred")
public class SpredController {

    @Autowired
    SpredService spredService;


    @GetMapping("getSpred")
    public FormData getSpred() {
        try {
            JSONArray result = spredService.getSpred();
            return new FormData(0, result.size(), "success", result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new FormData(500, 0, "获取现货差价失败: " + e.getMessage(), null);
        }
    }

    @GetMapping("getZbBinaSpred")
    public FormData getZbBinaSpred() {
        try {
            JSONArray result = spredService.getZbBinaSpred();
            return new FormData(0, result.size(), "success", result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new FormData(500, 0, "获取现货差价失败: " + e.getMessage(), null);
        }
    }

}
