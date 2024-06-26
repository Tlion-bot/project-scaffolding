package com.base.test.framework.config;

import cn.hutool.core.util.StrUtil;
import com.base.test.framework.config.properties.RedissonProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * redis配置
 *
 * @author Lion Li
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	private static final String REDIS_PROTOCOL_PREFIX = "redis://";
	private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

	@Autowired
	private RedisProperties redisProperties;

	@Autowired
	private RedissonProperties redissonProperties;

	@Bean(destroyMethod = "shutdown")
	@ConditionalOnMissingBean(RedissonClient.class)
	public RedissonClient redisson() throws IOException {
		String prefix = REDIS_PROTOCOL_PREFIX;
		if (redisProperties.isSsl()) {
			prefix = REDISS_PROTOCOL_PREFIX;
		}
		Config config = new Config();
		config.setThreads(redissonProperties.getThreads())
			.setNettyThreads(redissonProperties.getNettyThreads())
			.setCodec(JsonJacksonCodec.INSTANCE)
			.setTransportMode(redissonProperties.getTransportMode());

		RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
		// 使用单机模式
		config.useSingleServer()
			.setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
			.setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
			.setDatabase(redisProperties.getDatabase())
			.setPassword(StrUtil.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
			.setTimeout(singleServerConfig.getTimeout())
			.setRetryAttempts(singleServerConfig.getRetryAttempts())
			.setRetryInterval(singleServerConfig.getRetryInterval())
			.setSubscriptionsPerConnection(singleServerConfig.getSubscriptionsPerConnection())
			.setClientName(singleServerConfig.getClientName())
			.setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
			.setSubscriptionConnectionMinimumIdleSize(singleServerConfig.getSubscriptionConnectionMinimumIdleSize())
			.setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
			.setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
			.setConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
			.setDnsMonitoringInterval(singleServerConfig.getDnsMonitoringInterval());
		return Redisson.create(config);
	}

	/**
	 * 整合spring-cache
	 */
	@Bean
	public CacheManager cacheManager(RedissonClient redissonClient) {
		Map<String, CacheConfig> config = new HashMap<>();
		config.put("redissonCacheMap", new CacheConfig(30*60*1000, 10*60*1000));
		return new RedissonSpringCacheManager(redissonClient, config, JsonJacksonCodec.INSTANCE);
	}
	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
		RedisTemplate<String,Object> template = new RedisTemplate <>();
		template.setConnectionFactory(factory);

		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用jackson
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();

		return template;
	}



}
