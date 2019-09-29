package com.trtjk.all.config;

import com.trtjk.all.properties.RedisConfigProperties;
import com.trtjk.all.service.RedisOperClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedisPool;

/**
 * redis操作配置类
 * Created by Administrator on 2018/8/8.
 */
@Configuration  //开启配置
@EnableConfigurationProperties(value = {RedisConfigProperties.class})//开启使用映射实体对象
@ConditionalOnClass(value = {JedisPoolConfig.class, JedisPool.class})//存在JedisPoolConfig JedisPool时初始化该配置类
@Slf4j
public class RedisOperAutoConfiguration {

    @Autowired
    private RedisConfigProperties redisConfigProperties;



    public RedisOperAutoConfiguration(RedisConfigProperties redisConfigProperties) {
        this.redisConfigProperties = redisConfigProperties;
       // this.shardedRedisConfigProperties = shardedRedisConfigProperties;
    }
     // 此处是redis集群配置，单机版可忽略
    @Bean
    //@ConditionalOnMissingBean(ShardedJedisPool.class)
   // @ConditionalOnProperty(prefix = "spring.ha.redis",name = "USEHA",havingValue ="false") //配置文件是否存在spring.ha.redis的配置
    public JedisPool jedisPool() {
        log.info("加载jedisPool");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisConfigProperties.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisConfigProperties.getMaxIdel());
        jedisPoolConfig.setMinIdle(redisConfigProperties.getMinIdel());
        jedisPoolConfig.setTestOnBorrow(redisConfigProperties.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(redisConfigProperties.isTestOnRetrun());
        JedisPool  jedisPool = new JedisPool(jedisPoolConfig,redisConfigProperties.getRedis_server_ip(),redisConfigProperties.getRedis_server_port(),1000*1,redisConfigProperties.getRedis_pass(),0);
        return jedisPool;
    }



    @Bean
    @ConditionalOnBean(JedisPool.class) // 容器中存在JedisPool类则初始化该bean
    public RedisOperClient redisOperClient() {
        RedisOperClient redisOperClient = new RedisOperClient(jedisPool());
        return redisOperClient;
    }


}
