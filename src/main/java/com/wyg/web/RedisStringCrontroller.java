package com.wyg.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by A14857 on 2019/2/18.
 */
@RestController
@RequestMapping("/redis")
public class RedisStringCrontroller {
    private Log log = LogFactory.getLog(RedisStringCrontroller.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/setget.html")
    public String env(String key, String data){
        redisTemplate.opsForValue().set(key,data);
        String str = redisTemplate.opsForValue().get(key);
        log.info("str="+str);
        return str;
    }

    /**
     * springboot通过内置的序列化机制将字符串序列化成byte
     * @param key
     * @param value
     * @return
     */
    public String connetionSet(final String key, final String value){
        redisTemplate.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection conn) throws DataAccessException{

                try {
                    conn.set(key.getBytes(), value.getBytes());
                    //((StringRedisConnection)conn).set(key,value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        return "success";
    }
}
