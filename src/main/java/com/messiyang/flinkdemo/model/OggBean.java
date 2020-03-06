package com.messiyang.flinkdemo.model;

import com.alibaba.fastjson.JSONObject;

/**
 * @author cuiyang
 * @desc
 *
 * ogg kafka中消息
 */
public final class OggBean {

    private String table;
    private String op_type;
    private String op_ts;
    private String current_ts;
    private String pos;
    private String after;
    private String before;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    public String getOp_ts() {
        return op_ts;
    }

    public void setOp_ts(String op_ts) {
        this.op_ts = op_ts;
    }

    public String getCurrent_ts() {
        return current_ts;
    }

    public void setCurrent_ts(String current_ts) {
        this.current_ts = current_ts;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }


    public OggBean() {
    }

    public OggBean(String table, String op_type, String op_ts, String current_ts, String pos, String after, String before) {
        this.table = table;
        this.op_type = op_type;
        this.op_ts = op_ts;
        this.current_ts = current_ts;
        this.pos = pos;
        this.after = after;
        this.before = before;
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
