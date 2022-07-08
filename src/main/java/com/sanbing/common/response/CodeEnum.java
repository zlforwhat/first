package com.sanbing.common.response;

public enum CodeEnum {

    OK("200", "成功"),
    VALUE_NULL("300", "值为空"),
    PARAM_NULL("301", "参数为空，处理异常"),
    SIGN_ERROR("400", "签名错误"),
    NO_LOGIN("401", "未登录"),
    SYS_ERROR("500", "系统异常");

    private String code;
    private String msg;
    private CodeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
    public  static String getMsgByCode(String code){
        for(CodeEnum c: CodeEnum.values()){
            if(c.code.equals(code)){
                return c.msg;
            }
        }
        return null;
    }

}
