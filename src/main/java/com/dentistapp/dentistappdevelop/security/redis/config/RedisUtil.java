package com.dentistapp.dentistappdevelop.security.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisUtil {
    private int jwtExpirationMs;
    private int port;
    private String host;

    private final JedisPool pool;

    RedisUtil(@Value("${spring.redis.port}") int port, @Value("${spring.redis.host}") String host, @Value("${dentist.app.jwtExpirationMs}") int jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.port = port;
        this.host = host;
        System.out.println("------------ redis host: " + host + "-----------");
        System.out.println("------------ redis port: " + port + "-----------");
        pool = new JedisPool(new JedisPoolConfig(), host, port, jwtExpirationMs);
    }

    public void sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.srem(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


}


