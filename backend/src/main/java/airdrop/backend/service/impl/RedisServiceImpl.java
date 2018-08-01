package airdrop.backend.service.impl;

import airdrop.backend.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisServiceImpl implements RedisService {

    private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    JedisPool jedisPool;

    public Jedis getResource() {
        return jedisPool.getResource();
    }

    @SuppressWarnings("deprecation")
    public void returnResource(Jedis jedis) {
        if(jedis != null) {
            jedisPool.returnResourceObject(jedis);
        }
    }

    @Override
    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.set(key, value);
            jedis.expire(key, 3* 60);
            logger.info("redis set success - key: {} - value: {}", key, value);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("redis set error - key: {} - value: {}", key, value);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void setWithExpired(String key, String value, int seconds) {
        Jedis jedis = null;
        try{
            jedis = getResource();
            jedis.set(key, value);
            jedis.expire(key, seconds);
            logger.info("redis setWithExpired success - key: {} - value: {} - seconds: {}", key, value, seconds);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("redis setWithExpired error - key: {} - value: {}", key, value);
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getResource();
            value = jedis.get(key);
            logger.info("redis get success - key: {} - value: {}", key, value);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("redis get error - key: {}", key);
        } finally {
            returnResource(jedis);
            return value;
        }
    }

    @Override
    public long del(String key) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getResource();
            result = jedis.del(key);
            logger.info("redis get success - key: {} - result: {}", key, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("redis get error - key: {}", key);
        } finally {
            returnResource(jedis);
            return result;
        }
    }
}
