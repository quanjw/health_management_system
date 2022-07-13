package com.quanjiawei.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 检查组
 */
public class CheckGroup implements Serializable {
    private static final long serialVersionUID = -71044033958501520L;

    private Integer id;

    private String code;

    private String name;

    private String helpcode;

    private String sex;

    private String remark;

    private String attention;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelpcode() {
        return helpcode;
    }

    public void setHelpcode(String helpcode) {
        this.helpcode = helpcode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    @Override
    public String toString() {
        return "CheckGroup{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", helpcode='" + helpcode + '\'' +
                ", sex='" + sex + '\'' +
                ", remark='" + remark + '\'' +
                ", attention='" + attention + '\'' +
                '}';
    }
}
