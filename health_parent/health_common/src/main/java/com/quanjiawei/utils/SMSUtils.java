package com.quanjiawei.utils;

import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.teautil.*;
import com.aliyun.teautil.models.*;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	public static final String ACCESSKEY_ID = "LTAIak3CfAehK7cE";
	public static final String ACCESSKEY_SECRET = "zsykwhTIFa48f8fFdU06GOKjHWHel4";
	public static final String SIGNNAME = "签名";
	public static final String VALIDATE_CODE = "SMS_159620392";//发送短信验证码
	public static final String ORDER_NOTICE = "SMS_159771588";//体检预约成功通知


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
		com.aliyun.dysmsapi20170525.Client client = SMSUtils.createClient(ACCESSKEY_ID, ACCESSKEY_SECRET);
		SendSmsRequest sendSmsRequest = new SendSmsRequest()
				.setPhoneNumbers(phoneNumbers)
				.setSignName(SIGNNAME)
				.setTemplateCode(templateCode)
				.setTemplateParam(param);
		RuntimeOptions runtime = new RuntimeOptions();
		try {
			// 复制代码运行请自行打印 API 的返回值
			client.sendSmsWithOptions(sendSmsRequest, runtime);
		} catch (TeaException error) {
			// 如有需要，请打印 error
			com.aliyun.teautil.Common.assertAsString(error.message);
		} catch (Exception _error) {
			TeaException error = new TeaException(_error.getMessage(), _error);
			// 如有需要，请打印 error
			com.aliyun.teautil.Common.assertAsString(error.message);
		}
	}
}
