package com.markerhub.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code; // 200是正常，非200表示异常
    private String msg;
    private Object data;

    /**成功*/
    public static Result message(Object data) {
      return succ(200,"操作成功",data);
    }
    /**成功自定义*/
    public static Result message(String msg,Object data) {
      return succ(200,msg,data);
    }
    /**失败*/
    public static Result error(String msg) {
      return error(400,msg,null);
    }
    /**失败自定义结果
     * */
    public static Result error(String msg,Object data) {
      return error(400,msg,data);
    }

    /**成功结果*/
    public static Result succ(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    /**失败结果*/
    public static Result error(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
