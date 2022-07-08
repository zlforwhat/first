package com.sanbing.common.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = 8676131899637805509L;

    private String code;

    private String msg;

    private Object data;

    public ResponseResult(String code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
