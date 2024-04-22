package dev.nampd.hr_management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

//    @Cacheable: kết quả của phương thức được lưu vào cache.
//    Spring kiểm tra cache trước khi thực thi phương thức. Nếu đã tồn tại trong cache, kết quả được trả về từ cache
//    thường được sử dụng cho các phương thức đọc dữ liệu từ csdl
//
//    @CacheEvict: khi phương thức được gọi, các mục trong cache tương ứng sẽ được xóa
//    các mục trong cache sẽ được xóa trước khi phương thức được thực thi
//    thường được sử dụng cho các phương thức thay đổi dữ liệu hoặc khi cần làm sạch cache để cập nhật dữ liệu mới.
//
//    @CachePut: kết quả của phương thức được cập nhật hoặc thêm vào cache
//    kết quả của phương thức sẽ được lưu vào cache ngay sau khi phương thức được thực thi, ngay cả khi kết quả đã tồn tại trong cache trước đó.
//    thường được sử dụng khi cần đảm bảo rằng kết quả của một phương thức sẽ luôn được cập nhật trong cache, bất kể kết quả đã tồn tại hay không.

//    @Caching allows multiple nested @Cacheable, @CachePut and @CacheEvict to be used on the same method
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // Tạo Standalone Connection tới Redis
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // Tạo một RedisTemplate
        // Với Key là Object
        // Value là Object
        // RedisTemplate giúp chúng ta thao tác với Redis
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
//
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
//        template.afterPropertiesSet();
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60)));
        return builder.build();
    }

}

