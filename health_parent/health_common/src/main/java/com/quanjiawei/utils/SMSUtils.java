package com.quanjiawei.utils;

import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.teautil.models.*;
import com.quanjiawei.constant.DysmsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信发送工具类
 */
@Component
public class SMSUtils {

	private static String accesskeyId;
	private static String accesskeySecret;
	private static String signname;
	private static String validateCode;
	private static String orderNotice;

	@Autowired
	public void init( DysmsConstant dysmsConstant) {
		SMSUtils.accesskeyId = dysmsConstant.getAccesskeyId();
		SMSUtils.accesskeySecret = dysmsConstant.getAccesskeySecret();
		SMSUtils.signname = dysmsConstant.getSignname();
		SMSUtils.validateCode = dysmsConstant.getValidateCode();
		SMSUtils.orderNotice = dysmsConstant.getOrderNotice();
	}

	/**
	 * 使用AK&SK初始化账号Client
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @return Client
	 * @throws Exception
	 */
	public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
		Config config = new Config()
				// 您的 AccessKey ID
				.setAccessKeyId(accessKeyId)
				// 您的 AccessKey Secret
				.setAccessKeySecret(accessKeySecret);
		// 访问的域名
		config.endpoint = "dysmsapi.aliyuncs.com";
		return new com.aliyun.dysmsapi20170525.Client(config);
	}

	public static void sendShortMessage(String templateCode,String phoneNumbers,String param) throws Exception {
		com.aliyun.dysmsapi20170525.Client client = SMSUtils.createClient(accesskeyId, accesskeySecret);
		SendSmsRequest sendSmsRequest = new SendSmsRequest()
				.setPhoneNumbers(phoneNumbers)
				.setSignName(signname)
				.setTemplateCode(templateCode)
				.setTemplateParam(param);
		RuntimeOptions runtime = new RuntimeOptions();
		try {
			// 复制代码运行请自行打印 API 的返回值
			client.sendSmsWithOptions(sendSmsRequest, runtime);
		} catch (TeaException error) {
			// 如有需要，请打印 error
			com.aliyun.teautil.Common.assertAsString(error.message);
			error.printStackTrace();
		} catch (Exception _error) {
			TeaException error = new TeaException(_error.getMessage(), _error);
			// 如有需要，请打印 error
			com.aliyun.teautil.Common.assertAsString(error.message);
			error.printStackTrace();
		}
	}
}
