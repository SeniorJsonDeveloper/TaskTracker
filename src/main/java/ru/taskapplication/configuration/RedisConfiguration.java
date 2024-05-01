package ru.taskapplication.configuration;


import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.taskapplication.entity.RefreshTokenEntity;

import java.net.UnknownHostException;
import java.util.Collections;

@Configuration
public class RedisConfiguration {

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisProperties redisProperties) {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean
    public ReactiveRedisTemplate<String, RefreshTokenEntity> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<RefreshTokenEntity> valueJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(RefreshTokenEntity.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String,RefreshTokenEntity> builder =
                RedisSerializationContext.newSerializationContext(stringRedisSerializer);
        RedisSerializationContext<String,RefreshTokenEntity> context = builder.value(valueJackson2JsonRedisSerializer).build();

        return new ReactiveRedisTemplate<>(factory,context);
    }
}
