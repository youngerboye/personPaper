package com.response;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/*
前端异步调用时返回的json对象模型
 * */
@Getter
@Setter
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = 8992436576262574064L;

    /*
     * 是否成功
     * true:成功
     * false:失败
     * */
    private boolean success;

    /*
     * 是否成功
     * true:成功
     * false:失败
     * */
    private String message;

    /*
	详细消息 默认值空字符串
	 * */
    private String message_detail;

    /*
   	代码 默认值0
   	 * */
    private int code;

    /*
   	返回结果对象,默认值Null
   	 * */
    private Object data;


    public ResponseResult() {

    }

    public ResponseResult(boolean _success) {
        this.success = _success;
    }

    public ResponseResult(boolean _success, String _message) {
        this.success = _success;
        this.message = _message;
    }

    public ResponseResult(boolean success, Object data) {
        this.code = success ? 200 : 500;
        this.message = success ? "请求成功" : "请求失败";
        this.data = data;
        this.success = success;
    }

    public ResponseResult(boolean _success, String _message, Object _data) {
        this.success = _success;
        this.message = _message;
        this.data = _data;
    }

    public ResponseResult(boolean _success, String _message, int _code, Object _data) {
        this.success = _success;
        this.message = _message;
        this.code = _code;
        this.data = _data;
    }

    public static ResponseResult success() {
        return success(null);
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(true, data);
    }

    public static ResponseResult error(String message) {
        return new ResponseResult(false, message);
    }

    public static ResponseResult error(String message, int code) {
        return new ResponseResult(false, message, code, null);
    }

    @Override
    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
    }

}
