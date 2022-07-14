package com.quanjiawei.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * 七牛云工具类
 */
@PropertySource(value = {"classpath:application.properties"})
public class QiniuUtils {
    @Value("qiniu.accessKey")
    public  static String accessKey;
    @Value("qiniu.secretKey")
    public  static String secretKey;
    @Value("qiniu.bucket")
    public  static String bucket;

    public static void upload2Qiniu(String filePath,String fileName){
        Configuration cfg = new Configuration();
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucket);
        String key = fileName;
        try {
            Response response = uploadManager.put(filePath, key, token);
            //解析上传成功的结果
            DefaultPutRet defaultPutRet= JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println(defaultPutRet);
            System.out.println(defaultPutRet.hash);
            System.out.println(defaultPutRet.key);
        }catch (QiniuException ignored){

        }

    }

    //上传文件
    public static void upload2Qiniu(byte[] bytes, String fileName){
        Configuration cfg = new Configuration();
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucket);
        String key = fileName;
        try {
            Response response = uploadManager.put(bytes, key, token);
            //解析上传成功的结果
            DefaultPutRet defaultPutRet= JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println(defaultPutRet);
        }catch (QiniuException ignored){

        }
    }

    //删除文件
    public static void deleteFileFromQiniu(String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration();
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
