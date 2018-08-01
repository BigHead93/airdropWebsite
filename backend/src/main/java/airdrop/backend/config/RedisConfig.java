package airdrop.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value(("${spring.redis.pool.max-wait}"))
    private int maxWaitTimeMillis;

    @Bean
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(maxWaitTimeMillis);
        return config;
    }

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
//        JedisPool pool = new JedisPool(config, hostName, port, timeout, password);
        JedisPool pool = new JedisPool(config, hostName, port);
        logger.info("Start to init JedisPool ...");
        return pool;
    }

}
