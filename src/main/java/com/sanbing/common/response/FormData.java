package com.sanbing.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装表格数据
 */
@Data
public class FormData implements Serializable {

    private static final long serialVersionUID = 8061414640836195553L;

    private int code;

    private int count;

    private String msg;

    private Object data;

    public FormData(int code, int count, String msg, Object data) {
        this.code = code;
        this.count = count;
        this.msg = msg;
        this.data = data;
    }
}
