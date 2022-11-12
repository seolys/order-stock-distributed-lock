package seolnavy.order.infra.common.config.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("embeddedRedisConfig")
@RequiredArgsConstructor
public class RedissonConfig {

	private final RedisProperties redisProperties;

	@Bean
	public RedissonClient redissonClient() {
		final Config config = new Config();
		config.useSingleServer()
				.setAddress("redis://" + this.redisProperties.getHost() + ":" + this.redisProperties.getPort());

		return Redisson.create(config);
	}
}
