package cz.cvut.fel.APIGW.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;


@Configuration
public class LettuceConfig {

    private final String uri = "rediss://:pa762b10fc4b46a417fc1b8531afaba0f5a0a3df81d5824d27d78c6c42f044ed6@ec2-34-249-208-201.eu-west-1.compute.amazonaws.com:23540";
    @Bean
    public StatefulRedisConnection<String, String> connect() {
        RedisURI redisURI = RedisURI.create(uri);
        redisURI.setVerifyPeer(false);

        RedisClient redisClient = RedisClient.create(redisURI);
        return redisClient.connect();
    }

}
