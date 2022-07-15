package com.quanjiawei.jobs;

import com.quanjiawei.constant.RedisConstant;
import com.quanjiawei.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    private JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void clearImg(){
        Set<String> sdiffSet = jedisPool.getResource()
                .sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        if (sdiffSet != null){
            for (String imgNmae : sdiffSet) {
                QiniuUtils.deleteFileFromQiniu(imgNmae);
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,imgNmae);
                System.out.println("ClearImgJob.clearImg started...");
            }
        }

    }
}
