package org.example.api.clone;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 统一认证配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "authentication-config")
public class AuthenticationConfig {
	private String appId;
	private String appMark;
}
