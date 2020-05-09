package com.example.yingxievisitor.bean;

/**
 * 返回通用参数
 */
public class GMBean {

    /**
     * code :
     * data : null
     * message : 该账号已存在
     * status : false
     */

    private String code;
    private Object data;
    private String message;
    private boolean status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
