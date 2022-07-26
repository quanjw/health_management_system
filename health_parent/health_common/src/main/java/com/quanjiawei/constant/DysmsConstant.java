package com.quanjiawei.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:application.properties"})
public class DysmsConstant {

    @Value("${dysms.accesskeyId}")
    private  String accesskeyId;
    @Value("${dysms.accesskeySecret}")
    private  String accesskeySecret;
    @Value("${dysms.signname}")
    private  String signname;
    @Value("${dysms.validateCode}")
    private  String validateCode;
    @Value("${dysms.orderNotice}")
    private  String orderNotice;


    public DysmsConstant() {
    }

    public String getAccesskeyId() {
        return accesskeyId;
    }

    public void setAccesskeyId(String accesskeyId) {
        this.accesskeyId = accesskeyId;
    }

    public String getAccesskeySecret() {
        return accesskeySecret;
    }

    public void setAccesskeySecret(String accesskeySecret) {
        this.accesskeySecret = accesskeySecret;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getOrderNotice() {
        return orderNotice;
    }

    public void setOrderNotice(String orderNotice) {
        this.orderNotice = orderNotice;
    }
}
