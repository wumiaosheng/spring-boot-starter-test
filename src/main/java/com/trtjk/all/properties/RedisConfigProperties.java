package com.trtjk.all.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.redis")
@Data
public class RedisConfigProperties {

    /**
     * jedispool中jedis最大的可用实例
     */

    private Integer maxTotal = 50;

    /**
     * jedispool中jedis 最大空闲数
     */
    private Integer maxIdel = 20;

    /**
     * jedispool中最小空闲数
     */
    private Integer minIdel = 5;

    /**
     * 从连接池中借出的jedis都会经过测试
     */
    private boolean testOnBorrow = true;

    /**
     * 返回jedis到池中Jedis 实例都会经过测试
     */
    private boolean testOnRetrun = false;
    /**
     * IP
     */
    private String redis_server_ip;
    /**
     * port
     */
    private Integer redis_server_port;
    /**
     * 连接redis的password
     */
    private String redis_pass;

}
