package com.api.jaebichuri.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SshRedisConfig {

    private final SshTunnelingInitializer initializer;

    @Value("${ec2.redis_url}")
    private String redisUrl;

    @Value("${ec2.redis_port}")
    private int redisPort;

    @Value("${profile}")
    String profile;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String host = redisUrl;
        int port = redisPort;

        if (profile.equals("local")) {
            Integer forwardedPort = initializer.buildSshConnection(redisUrl, redisPort);
            host = "localhost";
            port = forwardedPort;
        }

        log.info("Redis connection through SSH: host={}, port={}", host, port);

        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public RedissonClient redissonClient() {
        String host = redisUrl;
        int port = redisPort;

        if (profile.equals("local")) {
            Integer forwardedPort = initializer.buildSshConnection(redisUrl, redisPort);
            host = "localhost";
            port = forwardedPort;
        }

        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);

        RedissonClient redissonClient = Redisson.create(config);
        return  redissonClient;
    }
}