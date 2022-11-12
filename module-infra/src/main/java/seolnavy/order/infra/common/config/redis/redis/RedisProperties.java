package seolnavy.order.infra.common.config.redis.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties("spring.redis")
public class RedisProperties {

	private String host;
	private Integer port;

}
